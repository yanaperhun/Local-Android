package com.local.app.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.local.app.BuildConfig
import com.local.app.LocalApp
import com.local.app.R
import com.local.app.data.AppException
import com.local.app.di.ComponentManager


open class BaseFragment : Fragment() {
    fun getDagger(): ComponentManager {
        return (activity?.application as LocalApp).daggerManager
    }

    open fun initUI(state: Bundle?) {}

    open fun subscribeToViewModel() {}

    open fun processError(error: AppException) {
        error.printStackTrace()
        if (BuildConfig.DEBUG) error.message?.let { showToast(error.message) }
    }

    fun log(mess: String) {
        Log.d("Local", "=============> $mess")
    }

    fun logError(mess: String) {
        Log.e("Local Error", "=============> $mess")
    }

    fun showFragment(fragment: Fragment, args: Bundle, addToBack: Boolean) {
        fragment.arguments = args
        (requireActivity() as BaseActivity).showFragment(fragment, addToBack, R.id.container)
    }

    fun showFragment(fragment: BaseFragment, addToBackStack: Boolean) {
        (requireActivity() as BaseActivity).showFragment(fragment, addToBackStack)
    }

    fun backStep() {
        requireActivity().supportFragmentManager.popBackStack()
    }

    fun showToast(message: String) {
        Toast
            .makeText(requireContext(), message, Toast.LENGTH_SHORT)
            .show()
    }

    open fun hideKeyboard() {
        val inputMethodManager: InputMethodManager =
            requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (requireActivity().currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(
                requireActivity().currentFocus!!.windowToken,
                0
            )
        }
    }

    fun showInfoAlert(message: String) {
        showAlert(getString(R.string.alert_title_info), message)
    }



    fun showErrorAlert(message: String?) {
        showAlert(
            getString(R.string.alert_title_error),
            message ?: "Что то пошло не так. Попробуйте еще раз"
        )
    }

    fun showAlert(title: String, message: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())

        builder
            .setMessage(message)
            .setTitle(title)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun showProgressDialog() {
        (requireActivity() as BaseActivity).showProgressDialog()
    }

    fun hideProgressDialog() {
        (requireActivity() as BaseActivity).hideProgressDialog()
    }

    fun focusET(editText: EditText) {
        editText.requestFocus()
        editText.setSelection(editText.text.length)
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

}