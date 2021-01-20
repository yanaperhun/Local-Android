package com.local.app

import android.content.Context
import android.net.Uri
import android.provider.MediaStore

fun Uri.getPathString(context: Context): String {
    var path: String = ""

    context.contentResolver.query(
        this, arrayOf(MediaStore.Images.Media.DATA),
        null, null, null
    )?.apply {
        val columnIndex = getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        moveToFirst()
        path = getString(columnIndex)
        close()
    }

    return path
}