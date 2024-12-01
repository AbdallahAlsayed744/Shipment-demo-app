package com.example.shipmentdemoapp.presentaion

import com.example.shipmentdemoapp.data.remote.dto.Model

sealed class LoginResult {
    object Idle : LoginResult()
    object Loading : LoginResult()
    data class Success(val data: Model) : LoginResult()
    data class Failure(val message: String) : LoginResult()
}