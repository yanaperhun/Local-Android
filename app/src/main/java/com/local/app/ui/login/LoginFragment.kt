package com.local.app.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.local.app.api.RetrofitClient
import com.local.app.databinding.FragmentLoginBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginFragment : Fragment() {

//    private var REQUEST_CODE_GOOGLE_AUTH = 1000
//    lateinit var binding: FragmentLoginBinding
//
//    override fun onCreateView(inflater: LayoutInflater,
//                              container: ViewGroup?,
//                              savedInstanceState: Bundle?): View? {
//        binding = FragmentLoginBinding.inflate(inflater)
//        return binding.root
//    }
//
//    override fun onResume() {
//        super.onResume()
//        binding.btnLogin.setOnClickListener {
//            var d = RetrofitClient().api
//                .load()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ Log.d("EVENTS", it.toString()) }, { it.printStackTrace() })
////            d.dispose()
//        }
//    }
//
//    private fun signIn() {
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken("63008644469-6ltsjkd5cs2q2mnf5gih15dobbe0bmto.apps.googleusercontent.com")
//            .requestEmail().build()
//        val mGoogleSignInClient = context?.let { GoogleSignIn.getClient(it, gso) }
//        val signInIntent: Intent = mGoogleSignInClient?.signInIntent as Intent
//
//        startActivityForResult(signInIntent, REQUEST_CODE_GOOGLE_AUTH)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_CODE_GOOGLE_AUTH) {
//
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            handleSignInResult(task)
//            print("Local app" + data?.data.toString())
//        }
//    }
//
//    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
//        try {
//            val account = completedTask.getResult(ApiException::class.java)
//            val s = account.toString()
//            Log.d("Local App",
//                  "Account name:${account?.displayName} token:${account?.idToken} Google id:${account?.id}")
//            // Signed in successfully, show authenticated UI.
//
//        } catch (e: ApiException) {
//            // The ApiException status code indicates the detailed failure reason.
//            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            e.printStackTrace()
//        }
//    }
}