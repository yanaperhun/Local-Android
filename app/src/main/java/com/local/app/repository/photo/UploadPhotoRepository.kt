package com.local.app.repository.photo

import com.local.app.data.photo.PhotoInDir
import io.reactivex.Single

interface UploadPhotoRepository {
    val photos : ArrayList<PhotoInDir>

    fun uploadImageFile(fileDir: String, userType : String): Single<PhotoInDir>
}