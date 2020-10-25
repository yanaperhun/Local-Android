package com.local.app.ui

import android.app.Activity
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.local.app.BuildConfig
import com.local.app.LocalApp
import com.local.app.R
import com.local.app.data.AppException
import com.local.app.di.ComponentManager

open class BaseFragment : Fragment() {
    fun getDagger(): ComponentManager {
        return (activity?.application as LocalApp).daggerManager
    }

    open fun initUI() {}

    open fun subscribeToViewModel() {}

    open fun processError(error: AppException) {
        error.printStackTrace()
        if (BuildConfig.DEBUG) error.message?.let { showToast(error.message) }
    }

    fun log(mess: String) {
        Log.d("Local", mess)
    }

    fun showFragment(fragment: BaseFragment, addToBackStack: Boolean) {
        (requireActivity() as BaseActivity).showFragment(fragment, addToBackStack)
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
            inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus!!.windowToken,
                                                       0)
        }
    }

    fun showInfoAlert(message: String) {
        showAlert(getString(R.string.alert_title_info), message)
    }

    fun showImage(imageView: ImageView, imageUrl: String) {
        Glide
            .with(imageView.context)
            .load(imageUrl)
            .into(imageView)
    }

    fun showRoundImage(imageView: ImageView, imageUrl: String) {
        Glide
            .with(imageView.context)
            .load(imageUrl)
            .circleCrop()
            .into(imageView)
    }

    fun showRounderCornersImage(imageView: ImageView, imageUrl: String, radius: Int) {
        Glide
            .with(imageView.context)
            .load(imageUrl)
            .transform(RoundedCorners(radius))
            .into(imageView)

    }

    fun showErrorAlert(message: String?) {
        showAlert(getString(R.string.alert_title_error),
                  message ?: "Что то пошло не так. Попробуйте еще раз")
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