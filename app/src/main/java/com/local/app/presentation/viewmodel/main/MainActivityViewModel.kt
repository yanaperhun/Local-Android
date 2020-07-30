package com.local.app.presentation.viewmodel.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.local.app.LocalApp
import com.local.app.data.User
import com.local.app.domain.profile.ProfileDomainFacade
import com.vk.api.sdk.VK
import com.vk.api.sdk.requests.VKRequest
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import javax.inject.Inject

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    val state = MutableLiveData<MainScreenState>()
    private val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var profileDomainFacade: ProfileDomainFacade

    init {
        getApplication<LocalApp>().daggerManager.plusLoginComponent()
        getApplication<LocalApp>().daggerManager.loginComponent?.inject(this)
    }

    fun getProfile(): Single<User> {
        return profileDomainFacade.getProfile()
    }

    fun isProfileLoaded(): Boolean {
        return profileDomainFacade.isProfileLoaded()
    }

    fun loadVKUser() {
        compositeDisposable.add(Observable
                                    .fromCallable {
                                        VK.executeSync(VKUsersRequest())
                                    }
                                    .subscribeOn(Schedulers.single())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe({
                                                   Log.d("AUTH", it.toString())  // response here
                                               }, {
                                                   it.printStackTrace()
                                                   // throwable here
                                               }))
    }

    class VKUsersRequest(uids: IntArray = intArrayOf()) : VKRequest<List<String>>("users.get") {

        init {
            if (uids.isNotEmpty()) {
                addParam("user_ids", uids.joinToString(","))
            }
            addParam("fields", "photo_200")
        }

        override fun parse(r: JSONObject): List<String> {
            Log.d("AUTH", "VK response $r")
            val users = r.getJSONArray("response")
            //            val result = ArrayList<String>()
            //            for (i in 0 until users.length()) {
            //                result.add(VKUser.parse(users.getJSONObject(i)))
            //            }
            return listOf(users.toString())
        }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        getApplication<LocalApp>().daggerManager.cleaLoginComponent()
        super.onCleared()
    }
}