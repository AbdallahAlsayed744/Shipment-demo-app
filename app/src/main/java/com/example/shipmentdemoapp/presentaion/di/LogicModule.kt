package com.example.shipmentdemoapp.presentaion.di

import com.example.shipmentdemoapp.data.local.TokenManager
import com.example.shipmentdemoapp.data.local.UserManager
import com.example.shipmentdemoapp.data.remote.networking.ApiService
import com.example.shipmentdemoapp.data.repositories.LoginRepositoryimpl
import com.example.shipmentdemoapp.data.repositories.RegisterRepositoryImpl
import com.example.shipmentdemoapp.data.repositories.ShipmentDataRepositoryimpl
import com.example.shipmentdemoapp.data.repositories.ShipmentRepositoryimpl
import com.example.shipmentdemoapp.domain.repositories.LoginRepository
import com.example.shipmentdemoapp.domain.repositories.RegisterRepository
import com.example.shipmentdemoapp.domain.repositories.ShipmentDataRepository
import com.example.shipmentdemoapp.domain.repositories.ShipmentRepository
import com.example.shipmentdemoapp.domain.usecase.GetCountriesUseCase
import com.example.shipmentdemoapp.domain.usecase.GetShipmentsUseCase
import com.example.shipmentdemoapp.domain.usecase.LoginUseCase
import com.example.shipmentdemoapp.domain.usecase.RefreshTokenUseCase
import com.example.shipmentdemoapp.domain.usecase.RegisterUseCase
import com.example.shipmentdemoapp.domain.usecase.RequestShipmentQuotationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LogicModule {


    @Provides
    @Singleton
    fun provideLoginRepository(api: ApiService,tokenManager: TokenManager,userManager: UserManager): LoginRepository {
        return LoginRepositoryimpl(api,tokenManager,userManager)
    }

    @Provides
    @Singleton
    fun provideRegisterRepository(api: ApiService): RegisterRepository {
        return RegisterRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideShipmentRepository(api: ApiService): ShipmentRepository {
        return ShipmentRepositoryimpl(api)
    }

    @Provides
    @Singleton
    fun provideShipmentDataRepository(api: ApiService): ShipmentDataRepository {
        return ShipmentDataRepositoryimpl(api)
    }






    @Provides
    @Singleton
    fun provideLoginUseCase(loginRepository: LoginRepository): LoginUseCase {
        return LoginUseCase(loginRepository)
    }

    @Provides
    @Singleton
    fun provideRefreshTokenUseCase(loginRepository: LoginRepository): RefreshTokenUseCase {
        return RefreshTokenUseCase(loginRepository)
    }

    @Provides
    @Singleton
    fun provideRegisterUseCase(registerRepository: RegisterRepository): RegisterUseCase {
        return RegisterUseCase(registerRepository)
    }

    @Provides
    @Singleton
    fun provideCountriesUseCase(registerRepository: RegisterRepository): GetCountriesUseCase {
        return GetCountriesUseCase(registerRepository)
    }

    @Provides
    @Singleton
    fun provideShipmentUseCase(shipmentRepository: ShipmentRepository): RequestShipmentQuotationUseCase {
        return RequestShipmentQuotationUseCase(shipmentRepository)
    }

    @Provides
    @Singleton
    fun provideShipmentDataUseCase(shipmentDataRepository: ShipmentDataRepository): GetShipmentsUseCase {
        return GetShipmentsUseCase(shipmentDataRepository)
    }


}