package com.example.shipmentdemoapp.data.remote.networking

import com.example.shipmentdemoapp.data.remote.dto.CountriesResponse
import com.example.shipmentdemoapp.data.remote.dto.LoginResponse
import com.example.shipmentdemoapp.data.remote.dto.QuotationResponse
import com.example.shipmentdemoapp.data.remote.dto.RegisterResponse
import com.example.shipmentdemoapp.data.remote.dto.ShipmentRequest
import com.example.shipmentdemoapp.data.remote.dto.ShipmentResponse
import com.google.gson.JsonObject

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("refresh")
    suspend fun refreshToken(
        @Body request: RefreshRequest
    ): Response<LoginResponse>


    @Headers("Content-Type: application/json")
    @POST("countries")
    suspend fun getCountries(
        @Header("lang") lang: String = "ar"
    ):Response<CountriesResponse>


    @Multipart
    @POST("register")
    suspend fun register(
        @Part("name") name: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("country_id") countryId: RequestBody,
        @Part("type") type: RequestBody,
        @Part file: MultipartBody.Part,
        @Part("token") token: RequestBody
    ):Response<RegisterResponse>

    @POST("requestQuotation")
    suspend fun requestShipmentQuotation(
        @Header("Authorization") token: String,
        @Body request: ShipmentRequest
    ): Response<QuotationResponse>


    @POST("getShippments")
    suspend fun getShipments(
        @Header("Authorization") token: String,
        @Header("lang") language: String,
        @Body request: JsonObject
    ): Response<ShipmentResponse>





    companion object{
        const val BASE_URL = "https://www.hyper-design.com/api/"
    }


}