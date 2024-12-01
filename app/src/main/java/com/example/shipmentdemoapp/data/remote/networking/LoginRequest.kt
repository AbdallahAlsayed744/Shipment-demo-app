package com.example.shipmentdemoapp.data.remote.networking

data class LoginRequest(
    val phone: String,
    val password: String,
    val token: String
)
