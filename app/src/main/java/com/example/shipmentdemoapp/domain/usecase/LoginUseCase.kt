package com.example.shipmentdemoapp.domain.usecase

import com.example.shipmentdemoapp.domain.repositories.LoginRepository

class LoginUseCase(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(email: String, password: String, token: String) = loginRepository.login(email, password, token)
}