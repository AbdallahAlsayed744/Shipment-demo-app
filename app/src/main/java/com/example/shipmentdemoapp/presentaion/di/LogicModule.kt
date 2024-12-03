package com.example.shipmentdemoapp.presentaion.di

import com.example.shipmentdemoapp.data.local.TokenManager
import com.example.shipmentdemoapp.data.local.UserManager
import com.example.shipmentdemoapp.data.remote.networking.ApiService
import com.example.shipmentdemoapp.data.repositories.LoginRepositoryimpl
import com.example.shipmentdemoapp.data.repositories.RegisterRepositoryImpl
import com.example.shipmentdemoapp.domain.repositories.LoginRepository
import com.example.shipmentdemoapp.domain.repositories.RegisterRepository
import com.example.shipmentdemoapp.domain.usecase.LoginUseCase
import com.example.shipmentdemoapp.domain.usecase.RefreshTokenUseCase
import com.example.shipmentdemoapp.domain.usecase.RegisterUseCase
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


}