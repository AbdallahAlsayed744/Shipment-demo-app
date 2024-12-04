package com.example.shipmentdemoapp.presentaion

import kotlinx.serialization.Serializable

sealed class ScreenRoutes(val route: String) {
    object LoginScreen : ScreenRoutes("login_screen")
    object RegisterScreen : ScreenRoutes("register_screen")
    data class HomeScreen(val userId: String, val refreshToken: String) : ScreenRoutes("home_screen/$userId/$refreshToken") {
        companion object {
            const val routeBase = "home_screen"
            const val userIdArg = "userId"
            const val tokenArg = "refreshToken"
            fun createRoute(userId: String, refreshToken: String): String {
                return "$routeBase/$userId/$refreshToken"
            }
        }
    }

    data class ShipmentQuotation(val token:String) : ScreenRoutes("shipment_quotation/$token") {
        companion object {
            const val routeBase = "shipment_quotation"
            const val tokenArg = "token"

            fun createRoute(token: String): String {
                return "$routeBase/$token"
            }
        }
    }

    data class ShipmentDetailsScreen(val shipmentId: String, val token: String) : ScreenRoutes("shipment_details_screen/$shipmentId/$token") {
        companion object {
            const val routeBase = "shipment_details_screen"
            const val shipmentIdArg = "shipmentId"
            const val tokenArg = "token"

            fun createRoute(shipmentId: String, token: String): String {
                return "$routeBase/$shipmentId/$token"
            }
        }
    }
}