package com.example.shipmentdemoapp.data.remote.dto

data class DetailsContainer(
    val id: Int,
    val container_number: String,
    val size: String,
    val weight: String,
    val dimension: String
)
