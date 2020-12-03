package com.local.app.api.requests

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class UploadFileBody(val photo: File) {

    var fileType: MultipartBody.Part = MultipartBody.Part.createFormData("fileType", "user_pic")
    var image: MultipartBody.Part = MultipartBody.Part
        .createFormData("file", "", photo.asRequestBody("multipart/form-data".toMediaTypeOrNull()))

}