package com.local.app.data.event.create

data class EventRaw(
        val title : String,
        val description : String,
        val tags : List<String>,
        val date : Long,
        val duration : Long,
        val price : Double,
        val address : EventAddress,
        val image  : String,
        val playlist : List<String>
)