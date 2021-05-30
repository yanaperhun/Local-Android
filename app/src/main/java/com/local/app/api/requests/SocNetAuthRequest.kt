package com.local.app.api.requests

data class SocNetAuthRequest(val token: String, val provider: String, val deviceName : String = "qa_ti")