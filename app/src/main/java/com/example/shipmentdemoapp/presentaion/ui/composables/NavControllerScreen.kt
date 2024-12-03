package com.example.shipmentdemoapp.presentaion.ui.composables

import android.widget.Toast
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.shipmentdemoapp.data.local.TokenManager
import com.example.shipmentdemoapp.presentaion.LoginResult
import com.example.shipmentdemoapp.presentaion.ScreenRoutes
import com.example.shipmentdemoapp.presentaion.viewmodel.LoginViewModel

@Composable
fun NavControllerScreen(
    startDestination: String,
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = startDestination) {
        // Login Screen
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

        // Register Screen
        composable(ScreenRoutes.RegisterScreen.route) {
            RegisterScreen(
                onLoginClick = {
                    navController.navigate(ScreenRoutes.LoginScreen.route)
                }
            )
        }

        // Home Screen with arguments
        composable(
            route = "${ScreenRoutes.HomeScreen.routeBase}/{${ScreenRoutes.HomeScreen.userIdArg}}/{${ScreenRoutes.HomeScreen.tokenArg}}",
            arguments = listOf(
                navArgument(ScreenRoutes.HomeScreen.userIdArg) { type = NavType.StringType },
                navArgument(ScreenRoutes.HomeScreen.tokenArg) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString(ScreenRoutes.HomeScreen.userIdArg) ?: ""
            val refreshToken = backStackEntry.arguments?.getString(ScreenRoutes.HomeScreen.tokenArg) ?: ""
            HomeScreen(userId = userId, token = refreshToken){
                navController.navigate(ScreenRoutes.ShipmentQuotation.createRoute(it))
            }
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
    }
}


