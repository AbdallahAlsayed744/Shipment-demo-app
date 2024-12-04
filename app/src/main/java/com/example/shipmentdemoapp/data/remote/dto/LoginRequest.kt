package com.example.shipmentdemoapp.data.remote.dto

data class LoginRequest(
    val phone: String,
    val password: String,
    val token: String
)
