package com.example.shipmentdemoapp.domain.repositories

import com.example.shipmentdemoapp.data.remote.dto.ShipmentDetailsRequest
import com.example.shipmentdemoapp.data.remote.dto.ShipmentDetailsResponse
import retrofit2.Response

interface ShipmentDetailsRepository {

    suspend fun getShipmentDetails(
        token: String,
        shipmentDetailsRequest: ShipmentDetailsRequest
    ): Response<ShipmentDetailsResponse>
}