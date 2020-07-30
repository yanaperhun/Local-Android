package com.local.app.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.local.app.R
import com.local.app.data.login.AuthProvider
import com.local.app.presentation.viewmodel.main.MainActivityViewModel
import com.local.app.ui.feed.FeedListFragment
import com.local.app.ui.filter.FilterDialogFragment
import com.local.app.ui.login.LoginDialog
import com.local.app.ui.login.LoginDialogCallback
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import timber.log.Timber

class MainActivity : BaseActivity() {

    private val REQUEST_CODE_GOOGLE_AUTH = 1000
    private val REQUEST_CODE_VK_AUTH = 1001
    private val REQUEST_CODE_INSTAGRAM_AUTH = 1002
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders
            .of(this)
            .get(MainActivityViewModel::class.java)


        if (savedInstanceState == null) {
            setContentView(R.layout.activity_main)
            showFragment(FeedListFragment(), true, R.id.container)
        }
    }

    fun onUserClick(view: View) {
        val loginDialog = LoginDialog()

        loginDialog.loginDialogCallback = loginDialogCallback

        loginDialog.show(supportFragmentManager, "login_dialog")
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
        }

        override fun onAuthSelected() {
        }

    }

    private fun startVKLogin() {
        VK.login(this, arrayListOf(VKScope.EMAIL, VKScope.FRIENDS))
    }

    private fun startGoogleLogin() {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(
                "63008644469-6ltsjkd5cs2q2mnf5gih15dobbe0bmto.apps.googleusercontent.com")
            .requestEmail()
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
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
                "Account name:${account.displayName} token:${account.idToken} Google id:${account.id}")
            viewModel.loadBySocialNetwork(account.idToken!!, AuthProvider.GOOGLE)


            // Signed in successfully, show authenticated UI.

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            e.printStackTrace()
        }
    }

    fun onFilterClick(view: View) {
        FilterDialogFragment().show(supportFragmentManager, "filters_dialog")
    }

}
