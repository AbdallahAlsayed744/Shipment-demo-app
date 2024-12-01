package com.example.shipmentdemoapp.presentaion.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(

): ViewModel() {

    private var _phoneNumber = MutableStateFlow("")
    val phoneNumber:StateFlow<String> get() = _phoneNumber

    private var _password = MutableStateFlow("")
    val password:StateFlow<String> get() = _password


    fun setPhoneNumber(phoneNumber:String){
        _phoneNumber.value = phoneNumber
    }

    fun setPassword(password:String){
        _password.value= password
    }


}