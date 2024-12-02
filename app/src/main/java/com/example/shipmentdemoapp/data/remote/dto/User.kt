package com.example.shipmentdemoapp.data.remote.dto

data class User(
    val id: Int,
    val name: String,
    val image: String,
    val type: String,
    val email: String,
    val admin: String,
    val active: String,
    val phone: String,
    val created_at: String,
    val updated_at: String
)
