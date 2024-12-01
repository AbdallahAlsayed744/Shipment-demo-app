package com.example.shipmentdemoapp.presentaion.di

import com.example.shipmentdemoapp.data.remote.networking.ApiService
import com.example.shipmentdemoapp.data.repositories.LoginRepositoryimpl
import com.example.shipmentdemoapp.domain.repositories.LoginRepository
import com.example.shipmentdemoapp.domain.usecase.LoginUseCase
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
    fun provideLoginRepository(api: ApiService): LoginRepository {
        return LoginRepositoryimpl(api)
    }


    @Provides
    @Singleton
    fun provideLoginUseCase(loginRepository: LoginRepository): LoginUseCase {
        return LoginUseCase(loginRepository)
    }
}