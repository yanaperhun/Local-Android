package com.local.app.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.local.app.R
import com.local.app.data.login.AuthProvider
import com.local.app.databinding.ActivityMainBinding
import com.local.app.presentation.viewmodel.main.MainActivityViewModel
import com.local.app.presentation.viewmodel.main.MainScreenState
import com.local.app.ui.BaseActivity
import com.local.app.ui.fragments.feed.EventsFeedFragment
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import timber.log.Timber

class MainActivity : BaseActivity() {


    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding
    var onLoginListener: OnLoginEnded? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProviders
            .of(this)
            .get(MainActivityViewModel::class.java)

        if (savedInstanceState == null) {
            showFragment(EventsFeedFragment(), true, R.id.container)
        }
        viewModel.state.observe(this, {
            when (it) {
                is MainScreenState.ProfileLoaded -> {
                    onLoginListener?.onLogin()
                }
                is MainScreenState.Error -> {
                    showToast(it.exception.message)
                    it.exception.printStackTrace()
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Timber.d("==> onActivityResult")

//        //if google
//        if (requestCode == EventsFeedFragment.REQUEST_CODE_GOOGLE_AUTH) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            handleSignInResult(task)
//        }

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

    interface OnLoginEnded {
        fun onLogin()
    }

}
