package com.example.shipmentdemoapp.presentaion.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shipmentdemoapp.data.remote.dto.Country
import com.example.shipmentdemoapp.domain.repositories.RegisterRepository
import com.example.shipmentdemoapp.domain.usecase.GetCountriesUseCase
import com.example.shipmentdemoapp.domain.usecase.RegisterUseCase
import com.example.shipmentdemoapp.presentaion.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val getCountriesUseCase: GetCountriesUseCase
) : ViewModel() {


    val registrationState = MutableStateFlow<RegisterState>(RegisterState.Idle)

    private val _countriesState = MutableStateFlow<List<Country>>(emptyList())
    val countriesState: StateFlow<List<Country>> get() = _countriesState


    private var _name = MutableStateFlow("")
    val name get() = _name
    private var _email = MutableStateFlow("")
    val email get() = _email
    private var _phone = MutableStateFlow("")
    val phone get() = _phone
    private var _password = MutableStateFlow("")
    val password get() = _password


    init {
        getCountries()
    }
    fun setName(name: String) {
        _name.value = name
    }

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPhone(phone: String) {
        _phone.value = phone
    }

    fun setPassword(password: String) {
        _password.value = password
    }




    fun register(
        name: String,
        email: String,
        phone: String,
        password: String,
        countryId: String,
        type: String,
        file: File,
        token: String
    ) {
        viewModelScope.launch {
            registrationState.value = RegisterState.Loading
            try {
                val nameBody = RequestBody.create("text/plain".toMediaTypeOrNull(), name)
                val emailBody = RequestBody.create("text/plain".toMediaTypeOrNull(), email)
                val phoneBody = RequestBody.create("text/plain".toMediaTypeOrNull(), phone)
                val passwordBody = RequestBody.create("text/plain".toMediaTypeOrNull(), password)
                val countryIdBody = RequestBody.create("text/plain".toMediaTypeOrNull(), countryId)
                val typeBody = RequestBody.create("text/plain".toMediaTypeOrNull(), type)
                val filePart = file.let {
                    MultipartBody.Part.createFormData(
                        "file",
                        it.name,
                        RequestBody.create("image/*".toMediaTypeOrNull(), it)
                    )

                }
                val tokenBody = RequestBody.create("text/plain".toMediaTypeOrNull(), token)

                val response = registerUseCase(
                    nameBody, emailBody, phoneBody, passwordBody,
                    countryIdBody, typeBody,filePart, tokenBody
                )

                if (response.isSuccessful) {
                    registrationState.value = RegisterState.Success(response.body()!!)
                    Log.d("RegisterViewModel", "Registration successful")
                } else {
                    registrationState.value = RegisterState.Failure("Registration failed")
                    Log.e("RegisterViewModel", "Registration failed")
                }
            } catch (e: Exception) {
                registrationState.value = RegisterState.Failure(e.message ?: "Unknown error")
            }
        }
    }

    fun getCountries() {
        viewModelScope.launch {
            try {
                val response = getCountriesUseCase()
                if (response.isSuccessful) {
                    _countriesState.value = response.body()?.countries ?: emptyList()
                } else {
                    Log.e("RegisterViewModel", "Failed to fetch countries: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("RegisterViewModel", "Error fetching countries: ${e.message}")
            }
        }
    }


}