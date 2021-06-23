package com.local.app.ui.fragments.feed

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.local.app.BindableFragment
import com.local.app.R
import com.local.app.data.event.Event
import com.local.app.data.login.AuthProvider
import com.local.app.databinding.FragmentFeedBinding
import com.local.app.presentation.viewmodel.feed.EventsFeedViewModel
import com.local.app.ui.activities.MainActivity
import com.local.app.ui.activities.event.EXTRAS_EVENT_ID
import com.local.app.ui.activities.event.EventActivity
import com.local.app.ui.dialog.login.LoginDialog
import com.local.app.ui.dialog.login.LoginDialogCallback
import com.local.app.ui.fragments.feed.state.FeedState
import com.local.app.ui.fragments.login.LoginFragment
import com.local.app.ui.fragments.profile.ProfileFragment
import com.local.app.utils.SimpleLoadingState
import com.local.app.utils.ViewUtils
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import com.yuyakaido.android.cardstackview.*
import timber.log.Timber

class EventsFeedFragment : BindableFragment<FragmentFeedBinding>() {
    private lateinit var cardManager: CardStackLayoutManager
    val viewModel: EventsFeedViewModel by viewModels()
    private var mIsLoadingData = false

    private fun loadNextPage() {
        Timber.d("loadNextPage")
        viewModel.loadFeed()
    }

    private val adapter = object : EventsFeedRVAdapter() {
        override fun onClicks(click: Clicks) {
            when (click) {
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
        initCardRV()
    }

    override fun onStart() {
        super.onStart()
        Timber.d("===> onStart")
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
                is SimpleLoadingState.Success -> updateUI()
            }
        })

        if (viewModel.isFeedEmpty()) viewModel.loadFeed()
        updateUI()
        val parentActivty = requireActivity() as MainActivity
        parentActivty.onLoginListener = callback
        binding.btnRefresh.setOnClickListener { loadNextPage() }
    }

    override fun onStop() {
        super.onStop()
        Timber.d("===> onStop")
        val parentActivty = requireActivity() as MainActivity
        parentActivty.onLoginListener = null
    }

    private fun showProgress(isShow: Boolean) {
        binding.progressBar.isVisible = isShow
    }

    private fun showFeed(it: List<Event>) {
        adapter.setEvents(it)
    }

    private fun likeEvent(eventId: Long) {
        viewModel.likeEvent(eventId)
    }

    private fun dislikeEvent(eventId: Long) {
        viewModel.dislikeEvent(eventId)
    }

    private fun openEventScreen(eventId: Long) {
        val i = Intent(context, EventActivity::class.java)
        i.putExtra(EXTRAS_EVENT_ID, eventId)
        startActivity(i)
    }

    private fun updateUI() {
        showUserAvatar()
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
                            binding.ivUser, it.url.lg, ViewUtils
                                .dpToPx(10)
                                .toInt(), R.drawable.ic_user
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

//        startActivity(Intent(requireContext(), CreateEventActivity::class.java))
    }

    private fun showLoginDialog() {
        val loginDialog = LoginDialog()
        loginDialog.loginDialogCallback = loginDialogCallback
        loginDialog.show(parentFragmentManager, "login_dialog")
    }

    private fun showProfileFragment() {
        showFragment(ProfileFragment(), true)
    }

    private fun openAuthScreen() {
        val args = Bundle()
        args.putBoolean(LoginFragment.IS_LOGIN, false)
        showFragment(LoginFragment(), args, true)
    }

    private fun emailLogin() {
        Timber.d("emailLogin")
        showFragment(LoginFragment(), true)
    }

    private fun startVKLogin() {
        Timber.d("start vk login")

        VK.login(requireActivity(), arrayListOf(VKScope.EMAIL, VKScope.FRIENDS, VKScope.OFFLINE))
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
        //BK login processing in activity
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
            viewModel.loginBySocialNetwork(account.idToken!!, AuthProvider.GOOGLE)


        } catch (e: ApiException) {
            e.printStackTrace()
        }
    }

    fun showProfileButton(isShow: Boolean) {
        binding.ivUser.isVisible = isShow
    }

    val callback = object : MainActivity.OnLoginEnded {
        override fun onLogin() {
            updateUI()
        }
    }

    fun initCardRV() {
        if (binding.rvEvents.layoutManager == null) {
            cardManager = initCardManager()
            binding.rvEvents.layoutManager = cardManager
            binding.rvEvents.onFlingListener = null
            binding.rvEvents.addOnScrollListener(onScrollListener)

            val snapHelper: SnapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(binding.rvEvents)
            binding.rvEvents.adapter = adapter

            binding.ivDislike.setOnClickListener {
                val setting = SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Right)
                    .setDuration(Duration.Normal.duration)
                    .setInterpolator(AccelerateInterpolator())
                    .build()
                cardManager.setSwipeAnimationSetting(setting)
                onDislike()
                binding.rvEvents.swipe()
            }
            binding.ivLike.setOnClickListener {
                val setting = SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Left)
                    .setDuration(Duration.Normal.duration)
                    .setInterpolator(AccelerateInterpolator())
                    .build()
                cardManager.setSwipeAnimationSetting(setting)
                onLiked()
                binding.rvEvents.swipe()
            }
        }
    }

    private fun onLiked() {
        val currentpos = cardManager.cardStackState.topPosition
        Timber.d("current pos ${cardManager.cardStackState.targetPosition} ${cardManager.cardStackState.topPosition}")
        likeEvent(adapter.events[currentpos].id)
    }

    private fun onDislike() {
        val currentpos = cardManager.cardStackState.topPosition
        Timber.d("current pos ${cardManager.cardStackState.targetPosition} ${cardManager.cardStackState.topPosition}")
        dislikeEvent(adapter.events[currentpos].id)
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
            emailLogin()
        }

        override fun onAuthSelected() {
            openAuthScreen()
        }
    }

    private fun initCardManager(): CardStackLayoutManager {
        val setting = SwipeAnimationSetting.Builder()
            .setDuration(Duration.Fast.duration)
            .setInterpolator(AccelerateInterpolator())
            .build()

        return CardStackLayoutManager(context, cardStackListener).apply {
            setMaxDegree(30f)

            setSwipeThreshold(0.2f)
            setDirections(Direction.HORIZONTAL)
            setCanScrollVertical(true)
            setCanScrollHorizontal(true)
            setSwipeAnimationSetting(setting)
        }
    }

    private val cardStackListener: CardStackListener by lazy {
        object : CardStackListener {
            override fun onCardDragging(direction: Direction?, ratio: Float) {
            }

            override fun onCardSwiped(direction: Direction?) {
            }

            override fun onCardRewound() {
            }

            override fun onCardCanceled() {
            }

            override fun onCardAppeared(view: View?, position: Int) {
                Timber.d("==> onCardAppeared : $position")
                if (position == adapter.events.size - 1) {
                    loadNextPage()
                }
            }

            override fun onCardDisappeared(view: View?, position: Int) {
                Timber.d("===> onCardDisappeared : $position")
                if (position == adapter.events.size - 1) {
                    binding.btnRefresh.isVisible = viewModel.feedState.value != FeedState.Loading
                }
            }

        }
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//        Timber.d("layoutManager.findFirstCompletelyVisibleItemPosition() ==
//                " + ll.findFirstCompletelyVisibleItemPosition())
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//            Timber.d("dx $dx dy $dy")
//            if (dx > 0) {
//                recyclerView.layoutManager?.let {
//                    val ll = it as CardStackLayoutManager
//                    val visibleItemCount: Int = ll.childCount
//                    val totalItemCount: Int = ll.itemCount
//                    val pastVisibleItems: Int = ll.cardStackState.targetPosition
////                    Timber.d("visibleItemCount $visibleItemCount totalItemCount $totalItemCount pastVisibleItems $pastVisibleItems")
//                    if (!mIsLoadingData) {
//                        if (visibleItemCount + pastVisibleItems >= totalItemCount && viewModel.hasNextPage()) {
//                            mIsLoadingData = true
//                            loadNextPage()
//                        }
//                    }
//                }
//            }
        }
    }

    companion object {
        val REQUEST_CODE_GOOGLE_AUTH = 1000
        val REQUEST_CODE_VK_AUTH = 1001
        val REQUEST_CODE_INSTAGRAM_AUTH = 1002
    }
}