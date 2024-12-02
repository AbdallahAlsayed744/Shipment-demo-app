package com.example.shipmentdemoapp.presentaion.ui.composables

import android.widget.Toast
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shipmentdemoapp.presentaion.LoginResult
import com.example.shipmentdemoapp.presentaion.viewmodel.LoginViewModel

@Composable
fun HomeScreen(loginViewModel: LoginViewModel = hiltViewModel(),token: String, userId: String) {
    val refreshState by loginViewModel.refreshResult.collectAsState()

//    LaunchedEffect(userId) {
//        // Fetch the refresh token and perform the token refresh logic
//        loginViewModel.refreshToken(userId)
//    }

    Text("Welcome back! Your token is: $token and User ID is: $userId")

//    LaunchedEffect(Unit) {
//        loginViewModel.refreshToken()
//    }
//
//    when (refreshState) {
//        is LoginResult.Idle -> {
//            Text("Welcome back!")
//        }
//        is LoginResult.Loading -> {
//            CircularProgressIndicator()
//        }
//        is LoginResult.Success -> {
//            val data = (refreshState as LoginResult.Success).data
//            Text("Token refreshed: ${data.access_token}")
//        }
//        is LoginResult.Failure -> {
//            Toast.makeText(
//                LocalContext.current,
//                (refreshState as LoginResult.Failure).message,
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//    }
}
