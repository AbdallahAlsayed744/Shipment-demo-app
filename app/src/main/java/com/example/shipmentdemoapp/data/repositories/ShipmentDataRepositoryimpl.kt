package com.example.shipmentdemoapp.data.repositories


import android.annotation.SuppressLint
import android.util.Log
import com.example.shipmentdemoapp.data.remote.dto.ShipmentResponse
import com.example.shipmentdemoapp.data.remote.networking.ApiService
import com.example.shipmentdemoapp.domain.repositories.ShipmentDataRepository
import com.google.gson.JsonObject

import retrofit2.Response

class ShipmentDataRepositoryimpl(
    private val apiService: ApiService
): ShipmentDataRepository {
    @SuppressLint("SuspiciousIndentation")
    override suspend fun getShipmentList(token: String, page: Int): Response<ShipmentResponse> {

            val request = JsonObject().apply {
                addProperty("page", page)
            }

            val response = apiService.getShipments(token, "en", request)
              Log.d("ShipmentDataRepositoryimpl", "getShipmentList: ${response.body()}")
            if (response.isSuccessful) {
//                response.body()
                Log.d("ShipmentDataRepositoryimpl", "getShipmentListsucess: ${response.body()}")
            }else{
                Log.d("ShipmentDataRepositoryimpl", "getShipmentListfail: ${response.errorBody()}")
            }


          return response
    }
}