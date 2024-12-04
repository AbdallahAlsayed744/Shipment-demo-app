package com.example.shipmentdemoapp.presentaion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shipmentdemoapp.data.local.TokenManager
import com.example.shipmentdemoapp.data.local.UserManager
import com.example.shipmentdemoapp.domain.usecase.LoginUseCase
import com.example.shipmentdemoapp.domain.usecase.RefreshTokenUseCase
import com.example.shipmentdemoapp.presentaion.utl.LoginResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val tokenManager: TokenManager,
    private val userManager: UserManager



): ViewModel() {

    private var _phoneNumber = MutableStateFlow("")
    val phoneNumber:StateFlow<String> get() = _phoneNumber

    private var _password = MutableStateFlow("")
    val password:StateFlow<String> get() = _password


    val userId: Flow<String?> = userManager.userId
    val refreshToken: Flow<String?> = tokenManager.refreshToken


    private var _token = MutableStateFlow("fcm-token")
    val token: StateFlow<String> get() = _token

    private var _loginResult = MutableStateFlow<LoginResult>(LoginResult.Idle)
    val loginResult: StateFlow<LoginResult> get() = _loginResult

    private val _refreshResult = MutableStateFlow<LoginResult>(LoginResult.Idle)
    val refreshResult: StateFlow<LoginResult> get() = _refreshResult



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

    init {
//        viewModelScope.launch {
//           refreshTokenUseCase.getRefreshToken()
//
//
//        }
    }


    fun login() {
        val phone = phoneNumber.value
        val passwordValue = password.value
        val tokenValue = token.value

        viewModelScope.launch(Dispatchers.IO) {
            if (phone.isEmpty() || passwordValue.isEmpty()) {
                _loginResult.value = LoginResult.Failure("Phone number or password cannot be empty!")
                return@launch
            }

            _loginResult.value = LoginResult.Loading

            try {
                val result = loginUseCase(phone, passwordValue, tokenValue)
                val responseBody = result.body()

                if (result.isSuccessful && responseBody != null) {

                    val user = responseBody.user
                    val accessToken = responseBody.access_token

                    if (user!=null && accessToken.isNotEmpty()) {

                        tokenManager.saveRefreshToken(accessToken)
                        userManager.saveUserId(user.id)

                        _loginResult.value = LoginResult.Success(responseBody)
                    } else {
                        _loginResult.value = LoginResult.Failure("Invalid response: Missing user or token.")
                    }
                } else {
                    _loginResult.value =
                        LoginResult.Failure("Login failed! ${result.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _loginResult.value = LoginResult.Failure("Error: ${e.message}")
            }
        }
    }


    fun refreshToken(refreshToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _refreshResult.value = LoginResult.Loading
            try {
                val result = refreshTokenUseCase.invoke(refreshToken)
                if (result.isSuccessful && result.body() != null) {
                    _refreshResult.value = LoginResult.Success(result.body()!!)
                } else {
                    _refreshResult.value = LoginResult.Failure("Token refresh failed")
                }
            } catch (e: Exception) {
                _refreshResult.value = LoginResult.Failure("Error: ${e.message}")
            }
        }
    }

}