package com.local.app.domain.photo

import com.local.app.repository.photo.UploadPhotoRepository
import io.reactivex.Single
import java.io.File

class UploadPhotoInteractor(private val repository: UploadPhotoRepository) {

    fun uploadPhoto(uri: String?): Single<String> {

        return repository.uploadImageFile(File(uri))
    }

}