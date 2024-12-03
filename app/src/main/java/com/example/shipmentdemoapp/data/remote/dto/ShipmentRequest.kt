package com.example.shipmentdemoapp.data.remote.dto

import com.example.shipmentdemoapp.presentaion.ui.composables.Container

data class ShipmentRequest(
    val shipment_name: String,
    val description: String,
    val quantity: Int,
    val containerNumber: List<Container>,
    val comment: String
)
