package com.local.app.ui.fragments.event.create

import androidx.fragment.app.activityViewModels
import androidx.viewbinding.ViewBinding
import com.local.app.BindableFragment
import com.local.app.presentation.viewmodel.event.create.CreateEventViewModel

abstract class BaseCreateEventFragment<T : ViewBinding> : BindableFragment<T> (){

    val model : CreateEventViewModel by activityViewModels()
}