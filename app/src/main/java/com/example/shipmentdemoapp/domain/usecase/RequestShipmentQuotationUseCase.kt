package com.example.shipmentdemoapp.domain.usecase

import com.example.shipmentdemoapp.data.remote.dto.QuotationResponse
import com.example.shipmentdemoapp.data.remote.dto.ShipmentRequest
import com.example.shipmentdemoapp.domain.repositories.ShipmentRepository
import retrofit2.Response

class RequestShipmentQuotationUseCase(
    private val repository: ShipmentRepository
) {
    suspend operator fun invoke(token: String, shipmentRequest: ShipmentRequest): Response<QuotationResponse> {
        return repository.requestShipmentQuotation(token, shipmentRequest)
    }
}
