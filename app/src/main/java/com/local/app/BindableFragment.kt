package com.local.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.local.app.ui.BaseFragment

open abstract class BindableFragment<T : ViewBinding> : BaseFragment() {

    lateinit var binding: T

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setBinding(inflater)
        initUI()
        return binding.root
    }

    abstract fun setBinding(inflater: LayoutInflater)

}