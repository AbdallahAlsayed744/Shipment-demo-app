package com.example.shipmentdemoapp.data.repositories

import android.util.Log
import com.example.shipmentdemoapp.data.remote.dto.CountriesResponse
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
        type: RequestBody,
        file: MultipartBody.Part,
        token: RequestBody
    ): Response<RegisterResponse> {

        val response = apiService.register(name, email, phone, password, countryId, type,file, token)
        try {

            if (response.isSuccessful && response.body() != null) {
                Log.d("RegisterRepositoryImpl", "registerSuccess: ${response.body()}")
            }
            else{
                Log.e("RegisterRepositoryImpl", "registerError: ${response.errorBody()?.string()}")
            }

        } catch (e: Exception) {
            Log.e("RegisterRepositoryImpl", "registerError: ${e.message.toString()}")


        }
        return response
    }


    override suspend fun getCountries(): Response<CountriesResponse> {
        val response = apiService.getCountries()
        try {

            if (response.isSuccessful && response.body() != null) {
                Log.d("RegisterRepositoryImpl", "getCountriesSuccess: ${response.body()}")
            }
            else{
                Log.e("RegisterRepositoryImpl", "getCountriesError: ${response.errorBody()?.string()}")
            }

        } catch (e: Exception) {
            Log.e("RegisterRepositoryImpl", "getCountriesError: ${e.message.toString()}")
            }
        return response

    }


}