package com.example.shipmentdemoapp.domain.repositories

import com.example.shipmentdemoapp.data.remote.dto.LoginResponse
import retrofit2.Response

interface LoginRepository
{
    suspend fun login(email: String, password: String,token:String): Response<LoginResponse>

    suspend fun refreshToken(refreshToken: String): Response<LoginResponse>

    suspend fun saveRefreshToken(token: String)
    suspend fun getRefreshToken(): String?




}