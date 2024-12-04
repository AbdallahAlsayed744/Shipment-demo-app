package com.example.shipmentdemoapp.data.repositories

import android.util.Log
import com.example.shipmentdemoapp.data.local.TokenManager
import com.example.shipmentdemoapp.data.local.UserManager
import com.example.shipmentdemoapp.data.remote.dto.LoginResponse
import com.example.shipmentdemoapp.data.remote.networking.ApiService
import com.example.shipmentdemoapp.data.remote.dto.LoginRequest
import com.example.shipmentdemoapp.data.remote.dto.RefreshRequest
import com.example.shipmentdemoapp.domain.repositories.LoginRepository
import kotlinx.coroutines.flow.first
import retrofit2.Response

class LoginRepositoryimpl(
    private val api: ApiService,
    private val tokenManager: TokenManager,
    private val userManger:UserManager

): LoginRepository
{
    override suspend fun login(email: String, password: String, token: String): Response<LoginResponse> {
        val request = LoginRequest(email, password, token)
        return try {
            val response = api.login(request)

            if (response.isSuccessful && response.body() != null) {
                Log.d("LoginRepositoryImpl", "Login Success: ${response.body()}")
                val loginResponse = response.body()!!
//                saveRefreshToken(loginResponse.access_token)
//                userManger.saveUserId(loginResponse.user.id)
//                response
            } else {
                Log.e("LoginRepositoryImpl", "Login Error: ${response.errorBody()?.string()}")
//                response
            }
            response
        } catch (e: Exception) {
            Log.e("LoginRepositoryImpl", "Login Failed: ${e.message}", e)
            throw e
        }
    }

   override suspend fun refreshToken (refreshToken: String): Response<LoginResponse> {
        val request = RefreshRequest(refreshToken)
        return try {
            val response = api.refreshToken(request)

            if (response.isSuccessful && response.body() != null) {
                Log.d("LoginRepositoryImpl", "Refresh Success: ${response.body()}")
                response
            } else {
                Log.e("LoginRepositoryImpl", "Refresh Error: ${response.errorBody()?.string()}")
                response
            }
        } catch (e: Exception) {
            Log.e("LoginRepositoryImpl", "Refresh Failed: ${e.message}", e)
            throw e
        }
    }

    override suspend fun saveRefreshToken(token: String) {
        tokenManager.saveRefreshToken(token)
    }


    override suspend fun getRefreshToken(): String? {
        return tokenManager.refreshToken.first() // Retrieving stored token
    }

}