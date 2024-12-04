package com.example.shipmentdemoapp.data.remote.dto


data class ShipmentDetails(

    val id: Int,
    val shipment_name: String,
    val description: String,
    val comment: String,
    val status: String,
    val Containers: List<DetailsContainer>

)
