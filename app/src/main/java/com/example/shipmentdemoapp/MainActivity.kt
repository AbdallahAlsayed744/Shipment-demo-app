package com.example.shipmentdemoapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.shipmentdemoapp.data.local.TokenManager
import com.example.shipmentdemoapp.data.local.UserManager
import com.example.shipmentdemoapp.presentaion.utl.ScreenRoutes
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
            val startDestination = remember { mutableStateOf<String?>(null) }

            LaunchedEffect(Unit) {
                val userId = userManager.userId.firstOrNull()
                val refreshToken = tokenManager.refreshToken.firstOrNull()

                Log.d("MainActivity", "Fetched userId: $userId, refreshToken: $refreshToken")

                startDestination.value = if (!userId.isNullOrEmpty() && !refreshToken.isNullOrEmpty()) {
                    ScreenRoutes.HomeScreen.createRoute(userId, refreshToken)
                } else {
                    ScreenRoutes.LoginScreen.route
                }
            }

            ShipmentDemoAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    when (val destination = startDestination.value) {
                        null -> {
                            LoadingScreen()
                        }
                        else -> {
                            NavControllerScreen(startDestination = destination)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
    }
}


