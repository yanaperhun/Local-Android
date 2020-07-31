package com.local.app.ui

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.local.app.BuildConfig
import com.local.app.LocalApp
import com.local.app.R
import com.local.app.data.AppException
import com.local.app.di.ComponentManager
import timber.log.Timber

open class BaseFragment : Fragment() {
    fun getDagger(): ComponentManager {
        return (activity?.application as LocalApp).daggerManager
    }


    open fun initUI() {}

    open fun subscribeToViewModel() {}

    open fun processError(error: AppException) {
        error.printStackTrace()
        if (BuildConfig.DEBUG)
            error.message?.let { showToast(error.message) }
    }

    fun log(mess: String) {
        Timber.i(mess)
    }

    fun showToast(message: String) {
        Toast
            .makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    open fun hideKeyboard() {
        val inputMethodManager: InputMethodManager =
            requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (requireActivity().currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(
                requireActivity().currentFocus!!.windowToken, 0
            )
        }
    }


    fun showInfoAlert(message: String) {
        showAlert(getString(R.string.alert_title_info), message)
    }

    fun showErrorAlert(message: String) {
        showAlert(getString(R.string.alert_title_error), message)
    }

    fun showAlert(title: String, message: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())

        builder
            .setMessage(message)
            .setTitle(title)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}