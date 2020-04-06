package com.local.app.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.local.app.LocalApp
import com.local.app.data.Photo
import com.local.app.domain.feed.GetPhotoByEventsInteractor
import javax.inject.Inject

class PhotoViewerViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var interactor: GetPhotoByEventsInteractor;

    init {
        getApplication<LocalApp>().daggerManager.feedComponent?.inject(this)
    }

    fun getPhotos(postId: Int): List<Photo> {
        return interactor.execute(postId)
    }

}