package com.local.app.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

open class BaseActivity : AppCompatActivity() {

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
}