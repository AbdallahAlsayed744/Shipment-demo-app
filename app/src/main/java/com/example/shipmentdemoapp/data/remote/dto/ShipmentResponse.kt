package com.example.shipmentdemoapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ShipmentResponse(
    @SerializedName("shippments") // Correct the spelling to match JSON
    val shipments: ShipmentList
)