package com.local.app.presentation.viewmodel.main

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Index
import com.local.app.data.User
import com.local.app.domain.profile.ProfileDomainFacade
import io.reactivex.Single
import javax.inject.Inject

class MainActivityViewModel : ViewModel() {

    val state = MutableLiveData<MainScreenState>()

    @Inject
    lateinit var profileDomainFacade: ProfileDomainFacade

    fun getProfile(): Single<User> {
        return profileDomainFacade.getProfile()
    }

    fun isProfileLoaded(): Boolean {
        return profileDomainFacade.isProfileLoaded()
    }
}