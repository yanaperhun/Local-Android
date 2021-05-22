package com.local.app.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.local.app.R


open class BaseActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            val w: Window = window
//            w.setFlags(
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//            )
//        }
    }

    private var pbDialog: ProgressDialog? = null

    fun showFragment(fragment: Fragment, addToBack: Boolean) {
        showFragment(fragment, addToBack, R.id.container)
    }

    fun showFragment(fragment: Fragment, args: Bundle, addToBack: Boolean) {
        fragment.arguments = args
        showFragment(fragment, addToBack, R.id.container)
    }

    fun showFragment(fragment: Fragment, addToBack: Boolean, containerId: Int) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_NONE)
        transaction.replace(containerId, fragment, fragment.javaClass.name)
        if (addToBack) {
            transaction.addToBackStack(null)
        }
        transaction.commitAllowingStateLoss()
    }

    fun showRounderCornersImage(imageView: ImageView, imageUrl: String, radius: Int) {
        Glide
            .with(imageView.context)
            .load(imageUrl)
            .transform(RoundedCorners(radius))
            .into(imageView)

    }

    fun showImage(imageView: ImageView, imageUrl: String) {
        Glide
            .with(imageView.context)
            .load(imageUrl)
            .into(imageView)
    }

    open fun showProgressDialog() {
        showProgressDialog(getString(R.string.please_wait))
    }

    open fun showProgressDialog(mess: String?) {
        runOnUiThread {
            if (pbDialog == null) {
                pbDialog = ProgressDialog(this@BaseActivity)
                pbDialog!!.setMessage(mess)
                pbDialog!!.setCancelable(false)
            }
            pbDialog?.let {
                if (!it.isShowing) it.show()
            }

        }
    }

    open fun hideProgressDialog() {
        runOnUiThread(Runnable {
            if (pbDialog == null) return@Runnable
            pbDialog?.let {
                if (it.isShowing) it.cancel()
            }
        })
    }

    fun showToast(message: String? = "") {
        Toast
            .makeText(this, message?:"", Toast.LENGTH_SHORT)
            .show()
    }
}