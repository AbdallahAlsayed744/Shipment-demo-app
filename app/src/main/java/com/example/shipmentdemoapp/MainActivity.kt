package com.example.shipmentdemoapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.shipmentdemoapp.data.local.TokenManager
import com.example.shipmentdemoapp.data.local.UserManager
import com.example.shipmentdemoapp.presentaion.ScreenRoutes
import com.example.shipmentdemoapp.presentaion.theme.ShipmentDemoAppTheme
import com.example.shipmentdemoapp.presentaion.ui.composables.NavControllerScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var tokenManager: TokenManager

    @Inject
    lateinit var userManager: UserManager



    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val startDestination = remember { mutableStateOf(ScreenRoutes.LoginScreen.route) }

            LaunchedEffect(Unit) {
                val userId = userManager.userId.firstOrNull()
                val refreshToken = tokenManager.refreshToken.firstOrNull()
                Log.d("MainActivity", "Fetched userId: $userId, refreshToken: $refreshToken")
//                tokenManager.clearAllData()
//                userManager.clearAllData()
                Log.d("MainActivity", "Fetched userId: $userId, refreshToken: $refreshToken")

                if (!userId.isNullOrEmpty() && !refreshToken.isNullOrEmpty()) {
                    startDestination.value = ScreenRoutes.HomeScreen.createRoute(userId, refreshToken)
                }else {
                    startDestination.value = ScreenRoutes.LoginScreen.route
                }
            }

            val context = LocalContext.current

            // Create an instance of UserManager or TokenManager to check if the user is logged in
            val userManager = UserManager(context)
            val tokenManager = TokenManager(context)

            // Check if the user is logged in by fetching the user ID or refresh token



            ShipmentDemoAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {

                    NavControllerScreen(startDestination =startDestination.value)

                }
            }
        }
    }
}

