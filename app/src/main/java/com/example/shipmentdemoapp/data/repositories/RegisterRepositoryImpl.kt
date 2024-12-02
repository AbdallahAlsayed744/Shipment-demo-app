package com.example.shipmentdemoapp.data.repositories

import com.example.shipmentdemoapp.data.remote.dto.RegisterResponse
import com.example.shipmentdemoapp.data.remote.networking.ApiService
import com.example.shipmentdemoapp.domain.repositories.RegisterRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class RegisterRepositoryImpl(
    private val apiService: ApiService
) : RegisterRepository {

    override suspend fun register(
        name: RequestBody,
        email: RequestBody,
        phone: RequestBody,
        password: RequestBody,
        countryId: RequestBody,
        file: MultipartBody.Part,
    ): Response<RegisterResponse> {
        return apiService.register(
            name = name,
            email = email,
            phone = phone,
            password = password,
            countryId = countryId,
            file = file,

        )
    }


}