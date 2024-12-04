package com.example.shipmentdemoapp.data.remote.dto

import com.example.shipmentdemoapp.presentaion.ui.composables.Container
import com.google.gson.annotations.SerializedName

data class Shipment(
    @SerializedName("id") val id: Int,
    @SerializedName("shipment_name") val shipmentName: String, // Match JSON field name
    @SerializedName("description") val description: String,
    @SerializedName("comment") val comment: String,
    @SerializedName("status") val status: String,
    @SerializedName("Containers") val containers: List<DetailsContainer> = emptyList()
)