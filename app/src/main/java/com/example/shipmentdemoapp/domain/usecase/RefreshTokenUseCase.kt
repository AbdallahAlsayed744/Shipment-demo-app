package com.example.shipmentdemoapp.domain.usecase

import com.example.shipmentdemoapp.domain.repositories.LoginRepository

class RefreshTokenUseCase(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(refreshToken: String) = loginRepository.refreshToken(refreshToken)

    suspend fun getRefreshToken() = loginRepository.getRefreshToken()
}