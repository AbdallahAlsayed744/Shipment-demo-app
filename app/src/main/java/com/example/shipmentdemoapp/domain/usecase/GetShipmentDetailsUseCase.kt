package com.example.shipmentdemoapp.domain.usecase

import com.example.shipmentdemoapp.data.remote.dto.ShipmentDetailsRequest
import com.example.shipmentdemoapp.data.remote.dto.ShipmentDetailsResponse
import com.example.shipmentdemoapp.domain.repositories.ShipmentDetailsRepository
import retrofit2.Response

class GetShipmentDetailsUseCase(
    private val shipmentDetailsRepository: ShipmentDetailsRepository
) {

    suspend operator fun invoke(token: String,shipmentDetailsRequest: ShipmentDetailsRequest): Response<ShipmentDetailsResponse>{
        return shipmentDetailsRepository.getShipmentDetails(token,shipmentDetailsRequest)
    }



}