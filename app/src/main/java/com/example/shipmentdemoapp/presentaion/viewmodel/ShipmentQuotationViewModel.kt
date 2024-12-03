package com.example.shipmentdemoapp.presentaion.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shipmentdemoapp.data.local.TokenManager
import com.example.shipmentdemoapp.data.remote.dto.QuotationResponse
import com.example.shipmentdemoapp.data.remote.dto.ShipmentRequest
import com.example.shipmentdemoapp.domain.usecase.RequestShipmentQuotationUseCase
import com.example.shipmentdemoapp.presentaion.RegisterState
import com.example.shipmentdemoapp.presentaion.ui.composables.Container
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShipmentQuotationViewModel @Inject constructor(

    private val shipmentQuotationUseCase: RequestShipmentQuotationUseCase,
    private val tokenManager: TokenManager


):ViewModel() {

    private var _shipmentName = MutableStateFlow("")
    val shipmentName get() = _shipmentName

    private val _quotationResponse = MutableStateFlow<QuotationResponse?>(null)
    val quotationResponse: StateFlow<QuotationResponse?> = _quotationResponse


    val token = tokenManager.refreshToken
    private var _shipmentDescription = MutableStateFlow("")
    val shipmentDescription get() = _shipmentDescription

    private var _shipmentQuntatiy = MutableStateFlow("")
    val shipmentQuntatiy get() = _shipmentQuntatiy

    private var _shipmentcomment = MutableStateFlow("")
    val shipmentcomment get() = _shipmentcomment

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun setErrorMessage(message: String) {
        _errorMessage.value = message
    }

    fun setShipmentName(shipmentName: String) {
        _shipmentName.value = shipmentName
    }
    fun setShipmentDescription(shipmentDescription: String) {
        _shipmentDescription.value = shipmentDescription
    }

    fun setShipmentQuntatiy(shipmentQuntatiy: String) {
        _shipmentQuntatiy.value = shipmentQuntatiy
    }

    fun setShipmentcomment(shipmentcomment: String) {
        _shipmentcomment.value = shipmentcomment
    }
    fun submitShipmentQuotation(
        containers: List<Container>,
        quantity: Int,
        mytoken: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = ""

            val request = ShipmentRequest(
                shipment_name = shipmentName.value,
                description = shipmentDescription.value,
                quantity = quantity,
                containerNumber = containers,
                comment = shipmentcomment.value
            )

            try {

                val response = shipmentQuotationUseCase("Bearer ${mytoken}", request)
                if (response.isSuccessful) {
                    _isLoading.value = false
                    onSuccess()
                } else {
                    _errorMessage.value = "Failed: ${response.message()}"
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message}"
                _isLoading.value = false
            }
        }
    }



}