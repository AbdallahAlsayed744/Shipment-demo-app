package com.example.shipmentdemoapp.presentaion.ui.composables

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.shipmentdemoapp.presentaion.utl.ScreenRoutes
import com.example.shipmentdemoapp.presentaion.viewmodel.LoginViewModel

@Composable
fun NavControllerScreen(
    startDestination: String,
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = startDestination) {

        composable(ScreenRoutes.LoginScreen.route) {
            LoginScreen(
                onClickReigster = {
                    navController.navigate(ScreenRoutes.RegisterScreen.route)
                },
                onClickHome = { token, userId ->
                    navController.navigate(ScreenRoutes.HomeScreen.createRoute(userId, token))
                }
            )
        }

        composable(ScreenRoutes.RegisterScreen.route) {
            RegisterScreen(
                onLoginClick = {
                    navController.navigateUp()
                }
            )
        }

        composable(
            route = "${ScreenRoutes.HomeScreen.routeBase}/{${ScreenRoutes.HomeScreen.userIdArg}}/{${ScreenRoutes.HomeScreen.tokenArg}}",
            arguments = listOf(
                navArgument(ScreenRoutes.HomeScreen.userIdArg) { type = NavType.StringType },
                navArgument(ScreenRoutes.HomeScreen.tokenArg) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString(ScreenRoutes.HomeScreen.userIdArg) ?: ""
            val refreshToken = backStackEntry.arguments?.getString(ScreenRoutes.HomeScreen.tokenArg) ?: ""
            HomeScreen(userId = userId, token = refreshToken, onShippingQuotationClick = { shipmentId ->
                navController.navigate(ScreenRoutes.ShipmentQuotation.createRoute(shipmentId))
            }, onShipDetailsScreen = { shipmentId ->
                navController.navigate(ScreenRoutes.ShipmentDetailsScreen.createRoute(refreshToken, shipmentId))
            })
        }

        composable(
           route= "${ScreenRoutes.ShipmentQuotation.routeBase}/{${ScreenRoutes.ShipmentQuotation.tokenArg}}",
            arguments = listOf(
                navArgument(ScreenRoutes.ShipmentQuotation.tokenArg) { type = NavType.StringType }
            )
        ) {backStackEntry->
            val refreshToken = backStackEntry.arguments?.getString(ScreenRoutes.ShipmentQuotation.tokenArg) ?: ""
            ShipmentQuotationScreen(
                onSubmitSuccess = {
                    Toast.makeText(context, "Shipment submitted successfully", Toast.LENGTH_SHORT).show()
                },
                token = refreshToken
            )

        }
        composable(
            route = "${ScreenRoutes.ShipmentDetailsScreen.routeBase}/{${ScreenRoutes.ShipmentDetailsScreen.tokenArg}}/{${ScreenRoutes.ShipmentDetailsScreen.shipmentIdArg}}",
            arguments = listOf(
                navArgument(ScreenRoutes.ShipmentDetailsScreen.tokenArg) { type = NavType.StringType },
                navArgument(ScreenRoutes.ShipmentDetailsScreen.shipmentIdArg) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString(ScreenRoutes.ShipmentDetailsScreen.tokenArg) ?: ""
            val shipmentId = backStackEntry.arguments?.getString(ScreenRoutes.ShipmentDetailsScreen.shipmentIdArg) ?: ""
            ShipmentDetailsScreen(shipmentId = shipmentId, token = token)
        }

    }


}


