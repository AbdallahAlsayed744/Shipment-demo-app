package com.example.shipmentdemoapp.domain.repositories

import com.example.shipmentdemoapp.data.remote.dto.QuotationResponse
import com.example.shipmentdemoapp.data.remote.dto.ShipmentRequest
import retrofit2.Response

interface ShipmentRepository {

    suspend fun requestShipmentQuotation(token: String, request: ShipmentRequest): Response<QuotationResponse>
}