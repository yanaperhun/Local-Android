package com.local.app.ui

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.local.app.R

open class BaseActivity : AppCompatActivity() {

    fun showFragment(fragment: Fragment, addToBack: Boolean) {
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


    fun showToast(message:  String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}