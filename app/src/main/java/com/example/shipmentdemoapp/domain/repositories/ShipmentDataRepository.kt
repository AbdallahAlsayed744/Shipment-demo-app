package com.example.shipmentdemoapp.domain.repositories

import com.example.shipmentdemoapp.data.remote.dto.ShipmentResponse
import com.example.shipmentdemoapp.data.remote.networking.ApiService
import retrofit2.Response

interface ShipmentDataRepository{

    suspend fun getShipmentList(token: String, page: Int): Response<ShipmentResponse>



}