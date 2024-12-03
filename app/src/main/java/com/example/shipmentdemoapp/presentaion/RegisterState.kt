package com.example.shipmentdemoapp.presentaion

import com.example.shipmentdemoapp.data.remote.dto.RegisterResponse

sealed class RegisterState {
    object Idle : RegisterState()
    object Loading : RegisterState()
    data class Success(val response: RegisterResponse) : RegisterState()
    data class Failure(val error: String) : RegisterState()
}