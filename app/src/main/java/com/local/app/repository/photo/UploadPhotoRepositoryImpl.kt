package com.local.app.repository.photo

import com.local.app.api.RetrofitClient
import com.local.app.api.requests.UploadFileBody
import com.local.app.data.photo.PhotoInDir
import io.reactivex.Single
import java.io.File

class UploadPhotoRepositoryImpl(val retrofitClient: RetrofitClient) : UploadPhotoRepository {

    override var photos = ArrayList<PhotoInDir>()

    override fun uploadImageFile(fileDir: String): Single<PhotoInDir> {
        photos.add(PhotoInDir(fileDir))
        val file = File(fileDir)
        return retrofitClient.api
            .loadImage(UploadFileBody(file))
            .doOnError {
                photos.forEach {
                    if (it.dir == fileDir) {
                        it.isError = true
                    }
                }
            }
            .map { photo ->
                photos.forEach {
                    if (it.dir == fileDir) {
                        it.hash = photo.hash
                        return@map it
                    }
                }
                return@map PhotoInDir("")
            }

    }
}