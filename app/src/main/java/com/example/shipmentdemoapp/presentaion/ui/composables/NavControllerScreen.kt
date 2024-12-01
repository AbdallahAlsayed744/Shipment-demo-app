package com.example.shipmentdemoapp.presentaion.ui.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shipmentdemoapp.presentaion.ScreenRoutes

@Composable
fun NavControllerScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ScreenRoutes.LoginScreen){
        composable<ScreenRoutes.LoginScreen> {
            LoginScreen(onClickReigster = {
                navController.navigate(ScreenRoutes.RegisterScreen)
            }, onClickHome = {
                navController.navigate(ScreenRoutes.HomeScreen)
            }
            )

        }

        composable<ScreenRoutes.RegisterScreen> {
            RegisterScreen()

        }

        composable<ScreenRoutes.HomeScreen> {
            
            Text(text = "Hello")

        }

    }
}