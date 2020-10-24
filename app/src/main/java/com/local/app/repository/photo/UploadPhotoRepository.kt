package com.local.app.repository.photo

import io.reactivex.Single
import java.io.File

interface UploadPhotoRepository {

    fun uploadImageFile(file : File): Single<String>
}