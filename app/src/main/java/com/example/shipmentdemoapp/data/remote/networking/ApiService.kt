package com.example.shipmentdemoapp.data.remote.networking

import com.example.shipmentdemoapp.data.remote.dto.Model
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("api/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<Model>

    @POST("api/refresh")
    suspend fun refreshToken(
        @Body request: RefreshRequest
    ): Response<Model>



    companion object{
        const val BASE_URL = "https://www.hyper-design.com/"
    }


}