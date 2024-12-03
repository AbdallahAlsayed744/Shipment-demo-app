package com.example.shipmentdemoapp.presentaion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shipmentdemoapp.data.remote.dto.Shipment
import com.example.shipmentdemoapp.domain.usecase.GetShipmentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

    private val getShipmentsUseCase: GetShipmentsUseCase

):ViewModel() {


    private val _shipments = MutableStateFlow<List<Shipment>>(emptyList())
    val shipments: StateFlow<List<Shipment>> = _shipments

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun getShipments(token: String, page: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val response = getShipmentsUseCase(token, page)
                if (response != null) {
                    _shipments.value = response.body()!!.shipments
                } else {
                    _errorMessage.value = "Failed to fetch shipments"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}