package com.local.app.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.local.app.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var REQUEST_CODE_GOOGLE_AUTH = 1000
    lateinit var binding: FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.btnGoogle.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        val mGoogleSignInClient = context?.let { GoogleSignIn.getClient(it, gso) }
        val signInIntent: Intent = mGoogleSignInClient?.signInIntent as Intent

        startActivityForResult(signInIntent, REQUEST_CODE_GOOGLE_AUTH)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_GOOGLE_AUTH) {
            print(data.toString())
        }
    }
}