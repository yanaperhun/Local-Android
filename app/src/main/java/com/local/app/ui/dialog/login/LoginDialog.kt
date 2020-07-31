package com.local.app.ui.dialog.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.local.app.R
import com.local.app.databinding.DialogLoginBinding

class LoginDialog : BottomSheetDialogFragment() {
    lateinit var loginDialogCallback: LoginDialogCallback
    private lateinit var binding: DialogLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = DialogLoginBinding.inflate(inflater)
        initUI()
        return binding.root
    }

    private fun initUI() {
        binding.btnInstagram.setOnClickListener { loginDialogCallback.onInstagramSelected() }
        binding.btnGoogle.setOnClickListener { loginDialogCallback.onGoogleSelected() }
        binding.btnVk.setOnClickListener { loginDialogCallback.onVkSelected() }
        binding.btnLoginByEmail.setOnClickListener { loginDialogCallback.onEmailSelected() }
        binding.btnSignIn.setOnClickListener { loginDialogCallback.onAuthSelected() }
    }

}
