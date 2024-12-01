package com.example.shipmentdemoapp.data.remote.dto

data class Auth(
    val bearer: List<Bearer>?,
    val type: String?
)