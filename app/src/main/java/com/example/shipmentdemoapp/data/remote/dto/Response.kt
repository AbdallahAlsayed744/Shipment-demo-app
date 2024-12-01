package com.example.shipmentdemoapp.data.remote.dto

data class Response(
    val _postman_previewlanguage: String,
    val body: String,
    val code: Int,
    val cookie: List<Any?>,
    val header: List<HeaderX>,
    val name: String,
    val originalRequest: OriginalRequest,
    val status: String
)