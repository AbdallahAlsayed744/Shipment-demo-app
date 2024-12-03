package com.example.shipmentdemoapp.data.remote.dto

data class LoginResponse(
    val access_token: String,
    val user: User,
    val message: String,
    val status: Int
)
