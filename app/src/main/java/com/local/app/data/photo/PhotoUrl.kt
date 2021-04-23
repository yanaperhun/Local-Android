package com.local.app.data.photo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotoUrl(var xs: String?, var sm: String?, var md: String?, var lg: String) : Parcelable