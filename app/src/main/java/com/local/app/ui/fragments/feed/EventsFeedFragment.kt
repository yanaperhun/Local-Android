package com.local.app.ui.fragments.feed

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.local.app.BindableFragment
import com.local.app.data.event.Event
import com.local.app.data.login.AuthProvider
import com.local.app.databinding.FragmentFeedBinding
import com.local.app.presentation.viewmodel.feed.EventsFeedViewModel
import com.local.app.ui.activities.event.EXTRAS_EVENT_ID
import com.local.app.ui.activities.event.EventActivity
import com.local.app.ui.dialog.login.LoginDialog
import com.local.app.ui.dialog.login.LoginDialogCallback
import com.local.app.ui.fragments.feed.state.FeedState
import com.local.app.ui.fragments.login.LoginFragment
import com.local.app.ui.fragments.profile.ProfileFragment
import com.local.app.utils.Utils
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.Direction
import org.ifpri.frani.ui.states.SimpleLoadingState
import timber.log.Timber

class EventsFeedFragment : BindableFragment<FragmentFeedBinding>() {

    val viewModel: EventsFeedViewModel by viewModels()

    private val adapter = object : EventsFeedRVAdapter() {
        override fun onClicks(click: Clicks) {
            when (click) {
                is Clicks.Dislike -> skipEvent(click.eventId)
                is Clicks.Like -> likeEvent(click.eventId)
                is Clicks.Event -> openEventScreen(click.eventId)
            }
        }
    }

    override fun setBinding(inflater: LayoutInflater) {
        binding = FragmentFeedBinding.inflate(inflater)
    }

    override fun initUI(state: Bundle?) {
        super.initUI(state)
        binding.ivUser.setOnClickListener { onUserClick() }
        binding.rvEvents.layoutManager = CardStackLayoutManager(context).apply {
            setMaxDegree(0f)
            setDirections(Direction.HORIZONTAL)
            setCanScrollVertical(false)
            setCanScrollHorizontal(true)
        }
        binding.rvEvents.onFlingListener = null
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvEvents)
        binding.rvEvents.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        viewModel.feedState.observe(this, {
            showProgress(it is FeedState.Loading)
            when (it) {
                is FeedState.Error -> it.throwable.printStackTrace()
                is FeedState.Success -> showFeed(it.event)
            }
        })

        viewModel.loginState.observe(this, {
            showProgress(it is SimpleLoadingState.Loading)
            when (it) {
                is SimpleLoadingState.Error -> showErrorAlert(it.error.message)
                is SimpleLoadingState.Success -> showUserAvatar()
            }
        })

        if (viewModel.isFeedEmpty()) viewModel.loadFeed()
        showUserAvatar()
    }

    private fun showProgress(isShow: Boolean) {
        binding.progressBar.isVisible = isShow
    }

    private fun showFeed(it: List<Event>) {
        adapter.setEvents(it)
    }

    private fun likeEvent(eventId: Long) {
        openEventScreen(eventId)
    }

    private fun openEventScreen(eventId: Long) {
        val i = Intent(context, EventActivity::class.java)
        i.putExtra(EXTRAS_EVENT_ID, eventId)
        startActivity(i)
    }

    private fun skipEvent(eventId: Long) {

    }

    private fun showUserAvatar() {
        viewModel
            .getProfile()
            ?.let { profile ->
                profile
                    .getProfileImage()
                    ?.let {
                        Log.d("MainActivity", "profile image : $it")
                        showRounderCornersImage(
                            binding.ivUser, it.url.lg, Utils
                                .dpToPx(10)
                                .toInt()
                        )
                    }

            }
    }

    fun onUserClick() {
        if (viewModel.isProfileLoaded()) {
            showProfileFragment()
        } else {
            showLoginDialog()
        }
    }

    private fun showLoginDialog() {
        val loginDialog = LoginDialog()
        loginDialog.loginDialogCallback = loginDialogCallback
        loginDialog.show(parentFragmentManager, "login_dialog")
    }

    private fun showProfileFragment() {
        showFragment(ProfileFragment(), true)
    }

    private val loginDialogCallback = object : LoginDialogCallback {

        override fun onVkSelected() {

            startVKLogin()
        }

        override fun onInstagramSelected() {

        }

        override fun onGoogleSelected() {
            startGoogleLogin()
        }

        override fun onEmailSelected() {
            openLoginScreen()
        }

        override fun onAuthSelected() {
            openAuthScreen()
        }

    }

    private fun openAuthScreen() {
        val args = Bundle()
        args.putBoolean(LoginFragment.IS_LOGIN, false)
        showFragment(LoginFragment(), args, true)
    }

    private fun openLoginScreen() {
        showFragment(LoginFragment(), true)
    }

    private fun startVKLogin() {
        Timber.d("start vk login")

        VK.login(requireActivity(), arrayListOf(VKScope.EMAIL, VKScope.FRIENDS))
    }

    private fun startGoogleLogin() {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(
                "63008644469-6ltsjkd5cs2q2mnf5gih15dobbe0bmto.apps.googleusercontent.com"
            )
            .requestEmail()
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        val signInIntent: Intent = mGoogleSignInClient?.signInIntent as Intent

        startActivityForResult(signInIntent, REQUEST_CODE_GOOGLE_AUTH)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //if google
        if (requestCode == REQUEST_CODE_GOOGLE_AUTH) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }

        //if VK
        val callback = object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                Timber.d(token.toString())
                Timber.d("LOG TOKEN")
                System.out.println("PRINTLN!!!!!!! ${token.email} ${token.accessToken}")
                viewModel.loadBySocialNetwork(token.accessToken, AuthProvider.VK)
            }

            override fun onLoginFailed(errorCode: Int) {
                // User didn't pass authorization
                showToast("Vk login failed error $errorCode")
            }
        }
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }

        //if instagram

    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            if (account == null || account.idToken.isNullOrEmpty()) {
                Timber.e("Google login error : account ir token is empty")
                showToast("Google login error : account ir token is empty")
                return
            }
            Timber.i(
                "Account name:${account.displayName} token:${account.idToken} Google id:${account.id}"
            )
            viewModel.loadBySocialNetwork(account.idToken!!, AuthProvider.GOOGLE)

            // Signed in successfully, show authenticated UI.

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            e.printStackTrace()
        }
    }

    fun showProfileButton(isShow: Boolean) {
        binding.ivUser.isVisible = isShow
    }

    companion object {
        val REQUEST_CODE_GOOGLE_AUTH = 1000
        val REQUEST_CODE_VK_AUTH = 1001
        val REQUEST_CODE_INSTAGRAM_AUTH = 1002
    }
}