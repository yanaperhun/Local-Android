package com.local.app.data.event.create

data class EventAddress(
        val address : String,
        val lat : Double = 0.0,
        val lng : Double  = 0.0
)