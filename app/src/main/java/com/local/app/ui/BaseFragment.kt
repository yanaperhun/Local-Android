package com.local.app.ui

import androidx.fragment.app.Fragment
import com.local.app.LocalApp
import com.local.app.di.ComponentManager

open class BaseFragment : Fragment() {
    fun getDagger(): ComponentManager {
        return (activity?.application as LocalApp).daggerManager
    }

}