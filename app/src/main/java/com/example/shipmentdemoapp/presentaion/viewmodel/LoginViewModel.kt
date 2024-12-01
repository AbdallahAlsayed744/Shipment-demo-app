package com.example.shipmentdemoapp.presentaion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shipmentdemoapp.domain.usecase.LoginUseCase
import com.example.shipmentdemoapp.presentaion.LoginResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase

): ViewModel() {

    private var _phoneNumber = MutableStateFlow("")
    val phoneNumber:StateFlow<String> get() = _phoneNumber

    private var _password = MutableStateFlow("")
    val password:StateFlow<String> get() = _password


    private var _token = MutableStateFlow("fcm-token") // Default token value
    val token: StateFlow<String> get() = _token

    private var _loginResult = MutableStateFlow<LoginResult>(LoginResult.Idle)
    val loginResult: StateFlow<LoginResult> get() = _loginResult



    fun setPhoneNumber(phoneNumber:String){
        _phoneNumber.value = phoneNumber
    }

    fun setPassword(password:String){
        _password.value= password
    }


    private var _showToast = MutableStateFlow(false)
    val showToast: StateFlow<Boolean> get() = _showToast



    fun setToken(newToken: String) {
        _token.value = newToken
    }

    fun setShowToast(value: Boolean) {
        _showToast.value = value
    }


    fun login() {
        val email = phoneNumber.value
        val passwordValue = password.value
        val tokenValue = token.value

        viewModelScope.launch(Dispatchers.IO) {


                if (email.isEmpty() || passwordValue.isEmpty()) {
                    _showToast.value = true
                    _loginResult.value = LoginResult.Failure("Phone number or password cannot be empty!")
                    return@launch
                }



            _loginResult.value = LoginResult.Loading


            val result = loginUseCase(email, passwordValue, tokenValue)


            if (result.isSuccessful && result.body() != null) {
                val body = result.body()
                if (body?.auth == null || body.event == null || body.info == null || body.item == null || body.variable == null) {
                    _loginResult.value = LoginResult.Failure("Login failed! Missing essential data in the response.")
                } else {
                    _loginResult.value = LoginResult.Success(body)
                }
            } else {
                _loginResult.value =
                    LoginResult.Failure("Login failed! ${result.errorBody()?.string()}")
            }
        }
    }
}