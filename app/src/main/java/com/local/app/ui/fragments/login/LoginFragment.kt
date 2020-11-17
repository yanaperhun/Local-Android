package com.local.app.ui.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.local.app.R
import com.local.app.data.AppException
import com.local.app.databinding.FragmentLoginBinding
import com.local.app.ui.BaseFragment
import com.local.app.utils.FieldValidation
import kotlin.jvm.Throws

class LoginFragment : BaseFragment() {

    private lateinit var viewModel: LoginViewModel

    companion object {
        const val IS_LOGIN = "is_login"
    }

    lateinit var binding: FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater)

        viewModel = ViewModelProviders
            .of(this)
            .get(LoginViewModel::class.java)
        initUI(savedInstanceState)
        subscribeToViewModel()
        return binding.root
    }

    override fun subscribeToViewModel() {
        viewModel.state.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility =
                if (it is LoginState.LOADING) View.VISIBLE else View.GONE

            when (it) {

                is LoginState.ERROR -> {
                    showErrorAlert(it.error.message ?: getString(R.string.alert_title_error))
                }
                is LoginState.SUCCESS -> {
                    goToMainScreen()
                }
            }
        })
    }

    private fun goToMainScreen() {

    }

    override fun initUI(state: Bundle?) {
        val isLoginFlow = arguments?.getBoolean(IS_LOGIN, true) ?: true

        if (isLoginFlow) {
            binding.groupAuth.visibility = View.GONE
        } else {
            binding.btnLogin.visibility = View.GONE
        }

        binding.btnLogin.setOnClickListener { login() }
        binding.btnCreateAccount.setOnClickListener { createAccount() }

    }

    private fun login() {
        try {
            if (preLoginValidate()) {
                viewModel.login(binding.etEmail.text.toString(), binding.etPassword.text.toString())
            }
        } catch (ex: AppException) {
            showErrorAlert(ex.message ?: getString(R.string.error_validation_common))
        }

    }

    private fun createAccount() {
        try {
            if (preAuthValidate()) {
                viewModel.auth(binding.etName.text.toString(), binding.etEmail.text.toString(),
                               binding.etPassword.text.toString())
            }
        } catch (ex: AppException) {
            showErrorAlert(ex.message ?: getString(R.string.error_validation_common))
        }

    }

    @Throws(AppException::class)
    private fun preLoginValidate(): Boolean {
        if (!FieldValidation.checkEmail(binding.etEmail.text.toString())) throw AppException(
            getString(R.string.error_validate_email))
        if (!FieldValidation.checkPass(binding.etPassword.text.toString())) {
            throw AppException(getString(R.string.error_validate_pass))
        }
        return true
    }

    @Throws(AppException::class)
    private fun preAuthValidate(): Boolean {
        if (!FieldValidation.checkName(binding.etName.text.toString())) {
            throw AppException(getString(R.string.error_validate_name))
        }
        return preLoginValidate()
    }

}

