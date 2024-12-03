package com.example.shipmentdemoapp.domain.usecase

import com.example.shipmentdemoapp.domain.repositories.RegisterRepository

class GetCountriesUseCase(
    private val registerRepository: RegisterRepository
) {
    suspend operator fun invoke()= registerRepository.getCountries()

}