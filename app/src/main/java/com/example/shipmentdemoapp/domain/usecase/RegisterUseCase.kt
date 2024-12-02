package com.example.shipmentdemoapp.domain.usecase

import com.example.shipmentdemoapp.domain.repositories.RegisterRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart

class RegisterUseCase(
    private val registerRepository: RegisterRepository
) {

    suspend operator fun invoke(
        name: RequestBody,
        email: RequestBody,
        phone: RequestBody,
        password: RequestBody,
        countryId: RequestBody,
        file: MultipartBody.Part
    ) = registerRepository.register(name, email, phone, password, countryId, file)


}