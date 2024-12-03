package com.example.shipmentdemoapp.data.remote.networking

import com.example.shipmentdemoapp.data.remote.dto.LoginResponse
import com.example.shipmentdemoapp.data.remote.dto.Model
import com.example.shipmentdemoapp.data.remote.dto.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
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


    @POST("countries")
    suspend fun getCountries(
        @Header("lang") lang: String = "ar"
    ): Response<List<String>>


    @Multipart
    @POST("register")
    suspend fun register(
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("password") password: RequestBody,
        @Part("country_id") countryId: RequestBody,
        @Part file: MultipartBody.Part,
    ):Response<RegisterResponse>



    companion object{
        const val BASE_URL = "https://www.hyper-design.com/api/"
    }


}