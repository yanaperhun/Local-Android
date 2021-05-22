package com.local.app.data

data class PlaceData(var placeId: String?, var fullText: String = "") {

    override fun toString(): String {
        return fullText
    }
}