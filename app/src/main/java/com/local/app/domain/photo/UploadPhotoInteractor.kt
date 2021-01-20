package com.local.app.domain.photo

import android.net.Uri
import com.local.app.data.photo.PhotoInDir
import com.local.app.repository.event.create.CreateEventRepository
import com.local.app.repository.photo.UploadPhotoRepository
import io.reactivex.Single
import javax.inject.Inject

class UploadPhotoInteractor @Inject constructor(private val repository: UploadPhotoRepository,
                                                private val createEventRepository: CreateEventRepository) {

    fun uploadPhoto(fileDir: String): Single<PhotoInDir> {

        return repository
            .uploadImageFile(fileDir)
            .doOnSuccess { createEventRepository.addPhoto(it) }
    }

    fun getPhotos(): List<PhotoInDir> {
        return repository.photos

    }

}