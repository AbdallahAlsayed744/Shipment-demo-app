package com.example.shipmentdemoapp.domain.usecase

import com.example.shipmentdemoapp.data.remote.dto.ShipmentResponse
import com.example.shipmentdemoapp.domain.repositories.ShipmentDataRepository

class GetShipmentsUseCase(
    private val shipmentRepository: ShipmentDataRepository
) {

    suspend operator fun invoke(token: String, page: Int) : retrofit2.Response<ShipmentResponse>{
        return shipmentRepository.getShipmentList(token, page)
    }



}