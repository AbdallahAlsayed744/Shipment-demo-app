package com.example.shipmentdemoapp.presentaion

import kotlinx.serialization.Serializable

sealed interface ScreenRoutes {

    @Serializable
    object LoginScreen : ScreenRoutes
    @Serializable
    object RegisterScreen : ScreenRoutes

    @Serializable
    object HomeScreen : ScreenRoutes
}