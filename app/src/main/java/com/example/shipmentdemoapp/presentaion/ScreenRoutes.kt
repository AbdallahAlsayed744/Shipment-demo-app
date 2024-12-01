package com.example.shipmentdemoapp.presentaion

sealed interface ScreenRoutes {

    object LoginScreen : ScreenRoutes
    object RegisterScreen : ScreenRoutes
    object HomeScreen : ScreenRoutes
}