package com.example.shipmentdemoapp.domain.repositories

import com.example.shipmentdemoapp.data.remote.dto.Model
import com.example.shipmentdemoapp.data.remote.networking.ApiService
import retrofit2.Response

interface LoginRepository
{
    suspend fun login(email: String, password: String,token:String): Response<Model>




}