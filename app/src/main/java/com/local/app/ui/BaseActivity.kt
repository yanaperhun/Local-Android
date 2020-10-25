package com.local.app.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.local.app.R

open class BaseActivity : AppCompatActivity() {

    fun showFragment(fragment: Fragment, addToBack: Boolean) {
        showFragment(fragment, addToBack, R.id.container)
    }

    fun showFragment(fragment: Fragment, args : Bundle, addToBack: Boolean) {
        fragment.arguments = args
        showFragment(fragment, addToBack, R.id.container)
    }

    fun showFragment(fragment: Fragment,
                     addToBack: Boolean,
                     containerId: Int) {
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

    fun showToast(message:  String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}