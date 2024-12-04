package com.example.shipmentdemoapp.data.repositories

import android.util.Log
import com.example.shipmentdemoapp.data.remote.dto.ShipmentDetailsRequest
import com.example.shipmentdemoapp.data.remote.dto.ShipmentDetailsResponse
import com.example.shipmentdemoapp.data.remote.networking.ApiService
import com.example.shipmentdemoapp.domain.repositories.ShipmentDetailsRepository
import retrofit2.Response

class ShipmentDetailsRepositoryimpl(
    private val apiService: ApiService
): ShipmentDetailsRepository {

    override suspend fun getShipmentDetails(
        token: String,
        shipmentDetailsRequest: ShipmentDetailsRequest
    ): Response<ShipmentDetailsResponse> {
       val response = apiService.getShipmentDetails(token,"en" ,shipmentDetailsRequest)
        if (response.isSuccessful&&response.body()!=null){
            Log.d("ShipmentDetailsRepositoryimpl", "getShipmentDetailssuccess: ${response.body()}")
        }else{
            Log.d("ShipmentDetailsRepositoryimpl", "getShipmentDetailsFaild: ${response.errorBody()}")
        }
        return response
    }
}