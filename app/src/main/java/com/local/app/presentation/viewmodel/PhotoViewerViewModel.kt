package com.local.app.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.local.app.LocalApp
import com.local.app.data.photo.PhotoEntity
import com.local.app.domain.feed.GetPhotoByEventsInteractor
import io.reactivex.Single
import javax.inject.Inject

class PhotoViewerViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var interactor: GetPhotoByEventsInteractor;

    init {
        getApplication<LocalApp>().daggerManager.feedComponent?.inject(this)
    }

    fun getPhotos(eventId: Long): Single<List<PhotoEntity>> {
        return interactor.execute(eventId)
    }

}