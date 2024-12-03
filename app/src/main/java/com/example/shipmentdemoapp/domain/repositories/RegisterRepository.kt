package com.example.shipmentdemoapp.domain.repositories

import com.example.shipmentdemoapp.data.remote.dto.CountriesResponse
import com.example.shipmentdemoapp.data.remote.dto.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

interface RegisterRepository {

    suspend fun register(
        name: RequestBody,
        email: RequestBody,
        phone: RequestBody,
        password: RequestBody,
        countryId: RequestBody,
        type: RequestBody,
        file: MultipartBody.Part,
        token: RequestBody
    ): Response<RegisterResponse>


    suspend fun getCountries(): Response<CountriesResponse>
}