package com.example.shipmentdemoapp.data.remote.dto

data class RegisterResponse(
    val message: String,
    val status: Int,
    val user: User
)

