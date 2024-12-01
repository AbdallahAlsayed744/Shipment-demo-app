package com.example.shipmentdemoapp.data.repositories

import android.util.Log
import com.example.shipmentdemoapp.data.remote.dto.Model
import com.example.shipmentdemoapp.data.remote.networking.ApiService
import com.example.shipmentdemoapp.data.remote.networking.LoginRequest
import com.example.shipmentdemoapp.domain.repositories.LoginRepository
import retrofit2.Response

class LoginRepositoryimpl(
    private val api: ApiService
): LoginRepository
{
    override suspend fun login(email: String, password: String, token: String): Response<Model> {
        val request = LoginRequest(email, password, token)
        return try {
            val response = api.login(request)

            if (response.isSuccessful && response.body() != null) {
                Log.d("LoginRepositoryImpl", "Login Success: ${response.body()}")
                response
            } else {
                Log.e("LoginRepositoryImpl", "Login Error: ${response.errorBody()?.string()}")
                response
            }
        } catch (e: Exception) {
            Log.e("LoginRepositoryImpl", "Login Failed: ${e.message}", e)
            throw e
        }
    }

}