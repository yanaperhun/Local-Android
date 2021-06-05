package com.local.app.api.requests

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class UploadFileBody(val photo: String, val photoType: String) {

    var fileType: MultipartBody.Part = MultipartBody.Part.createFormData("fileType", photo)
    private val file = File(photo)
    private val requestFile: RequestBody =
        File(photo).asRequestBody("multipart/form-data".toMediaTypeOrNull())

    var image: MultipartBody.Part =
        MultipartBody.Part.createFormData("file", file.name, requestFile)

}