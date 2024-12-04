package com.example.shipmentdemoapp.presentaion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shipmentdemoapp.data.remote.dto.ShipmentDetailsRequest
import com.example.shipmentdemoapp.data.remote.dto.ShipmentDetailsResponse
import com.example.shipmentdemoapp.domain.usecase.GetShipmentDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShipmentDetailsViewModel @Inject constructor(
    private val  getShipmentDetailsUseCase: GetShipmentDetailsUseCase

) : ViewModel() {

    private val _shipmentDetails = MutableStateFlow<ShipmentDetailsResponse?>(null)
    val shipmentDetails: StateFlow<ShipmentDetailsResponse?> = _shipmentDetails

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage
    fun fetchShipmentDetails(token: String, shipmentId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val response = getShipmentDetailsUseCase(token, ShipmentDetailsRequest(shipmentId))
                if (response.isSuccessful) {
                    _shipmentDetails.value = response.body()
                } else {
                    _errorMessage.value = "Error: ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}