package com.local.app.data.event.create

data class EventAddress(
        val name : String,
        val address : String,
        val lat : Double = 0.0,
        val lng : Double  = 0.0,
        val city : Int = 0
)