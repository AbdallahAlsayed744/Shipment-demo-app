package com.example.shipmentdemoapp.data.repositories

import android.util.Log
import com.example.shipmentdemoapp.data.remote.dto.QuotationResponse
import com.example.shipmentdemoapp.data.remote.dto.ShipmentRequest
import com.example.shipmentdemoapp.data.remote.networking.ApiService
import com.example.shipmentdemoapp.domain.repositories.ShipmentRepository
import retrofit2.Response

class ShipmentRepositoryimpl(
    private val apiService: ApiService
): ShipmentRepository {

    override suspend fun requestShipmentQuotation(
        token: String,
        request: ShipmentRequest
    ): Response<QuotationResponse> {
        return try {
            val response = apiService.requestShipmentQuotation(token, request)
            Log.e("ShipmentRepositoryImpl", "Request: ${response.body()}")

            if (response.isSuccessful) {
                // If response is successful, return the response
                Log.e("ShipmentRepositoryImpl", "Request success: ${response.body()}")


                response
            } else {
                // Log the error response
                val errorBody = response.errorBody()?.string() // Extract error body as string
                Log.e("ShipmentRepositoryImpl", "Request failed with error: $errorBody")
                response // You can also handle errors differently if needed
            }
        } catch (e: Exception) {
            // Handle network or other exceptions
            Log.e("ShipmentRepositoryImpl", "Exception: ${e.localizedMessage}")
            throw e // Optionally rethrow the exception
        }
    }
}