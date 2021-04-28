package com.local.app.presentation.viewmodel.feed

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.local.app.LocalApp
import com.local.app.data.AppException
import com.local.app.data.Profile
import com.local.app.data.login.AuthProvider
import com.local.app.domain.feed.LoadFeedInteractor
import com.local.app.domain.profile.MainActivityDomainFacade
import com.local.app.ui.fragments.feed.state.FeedState
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.ifpri.frani.ui.states.SimpleLoadingState
import org.ifpri.frani.utils.SingleLiveEvent
import javax.inject.Inject

class EventsFeedViewModel(application: Application) : AndroidViewModel(application) {

    private val disposable: CompositeDisposable = CompositeDisposable()

    var feedState: SingleLiveEvent<FeedState> = SingleLiveEvent()
    val loginState = SingleLiveEvent<SimpleLoadingState<Profile>>()

    @Inject
    lateinit var getEventsInteractor: LoadFeedInteractor

    @Inject
    lateinit var profileDomainFacade: MainActivityDomainFacade

    init {
        getApplication<LocalApp>().daggerManager.plusFeedComponent()
//        getApplication<LocalApp>().daggerManager.plusLoginComponent()
        getApplication<LocalApp>().daggerManager.feedComponent?.inject(this)
    }

    fun loadFeed() {
        disposable.add(getEventsInteractor
                .loadFeed()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ feedState.value = FeedState.Success(it) },
                        { feedState.value = FeedState.Error(it) }))
    }

    fun getProfileAsync(): Single<Profile> {
        return profileDomainFacade.getProfileAsync()
    }

    fun getProfile(): Profile? {
        return profileDomainFacade.getProfile()
    }

    fun isProfileLoaded(): Boolean {
        return profileDomainFacade.isProfileLoaded()
    }

    fun loadBySocialNetwork(token: String, provider: AuthProvider) {
        disposable.add(profileDomainFacade
                .loginBySocNetworks(token, provider)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ loginState.postValue(SimpleLoadingState.Success(it)) }, {
                    it.printStackTrace()
                    loginState.value = SimpleLoadingState.Error(AppException(it.message
                            ?: "Ошибка при попытке авторизации token: $token, AuthProvider : ${provider.title}"))
                }))
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    fun isFeedEmpty(): Boolean {
        return getEventsInteractor.isFeedEmpty()
    }

}