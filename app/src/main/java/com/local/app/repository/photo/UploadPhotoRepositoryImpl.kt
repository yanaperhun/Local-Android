package com.local.app.repository.photo

import com.local.app.api.RetrofitClient
import io.reactivex.Single
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class UploadPhotoRepositoryImpl(val retrofitClient: RetrofitClient) : UploadPhotoRepository {
    override fun uploadImageFile(file : File): Single<String> {
        val requestFile: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())

        // MultipartBody.Part is used to send also the actual file name
        val body: MultipartBody.Part =
            MultipartBody.Part.createFormData("photo", file.name, requestFile)

        return retrofitClient.api.loadImage(body)
    }
}