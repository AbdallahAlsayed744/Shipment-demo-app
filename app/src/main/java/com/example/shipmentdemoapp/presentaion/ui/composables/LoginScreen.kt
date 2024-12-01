package com.example.shipmentdemoapp.presentaion.ui.composables

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shipmentdemoapp.R
import com.example.shipmentdemoapp.presentaion.LoginResult
import com.example.shipmentdemoapp.presentaion.viewmodel.LoginViewModel

@Composable
fun LoginScreen(loginViewModel:LoginViewModel= hiltViewModel(), onClickReigster: () -> Unit,onClickHome: () -> Unit) {
    val phoneState by loginViewModel.phoneNumber.collectAsState()
    val passwordState by loginViewModel.password.collectAsState()
    val loginResult by loginViewModel.loginResult.collectAsState()
    val showToast by loginViewModel.showToast.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(showToast) {
        if (showToast) {
            Toast.makeText(
                context,
                "Phone number or password cannot be empty!",
                Toast.LENGTH_SHORT
            ).show()
            loginViewModel.setShowToast(false) // Reset showToast state
        }
    }




    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        , verticalArrangement = Arrangement.Center
        , horizontalAlignment = Alignment.CenterHorizontally

    ) {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = phoneState,
            onValueChange = {
               loginViewModel.setPhoneNumber(it)

            },
            label = {
                Text(text = stringResource(R.string.phone))
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            maxLines = 1
        )

        Spacer(modifier =Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = passwordState,
            onValueChange = {
                 loginViewModel.setPassword(it)

            },
            label = {
                Text(text = stringResource(R.string.password))
            },
            visualTransformation = PasswordVisualTransformation(),
            maxLines = 1
        )


        Spacer(modifier =Modifier.height(10.dp))



        Button(modifier = Modifier, shape = RoundedCornerShape(3.dp),onClick = {
//            viewModel.login(phone, password) {
//                onLoginSuccess()
//            }
            loginViewModel.login()
        }) {
            Text(text = stringResource(R.string.login))
        }

        when (loginResult) {
            is LoginResult.Idle -> {
                Text("Please enter your credentials.")
            }

            is LoginResult.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            is LoginResult.Success -> {
                onClickHome() // Navigate to the home screen
            }

            is LoginResult.Failure -> {
                // Show Toast when login fails
                LaunchedEffect(loginResult) {
                    Toast.makeText(
                        context,
                        (loginResult as LoginResult.Failure).message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        Spacer(modifier =Modifier.height(17.dp))

        Row {
            Text(text = stringResource(R.string.no_account))
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                modifier = Modifier.clickable {
                    onClickReigster()
                },
                text = stringResource(R.string.register),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        when (loginResult) {
            is LoginResult.Idle -> {

            }
            is LoginResult.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is LoginResult.Success -> {
//                Text("Welcome! ${(loginResult as LoginResult.Success).data.auth.type}")
                onClickHome()
            }
            is LoginResult.Failure -> {

                LaunchedEffect(loginResult) {
                    // Show a toast on failure
                    Toast.makeText(
                        context,
                        (loginResult as LoginResult.Failure).message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
//                Toast.makeText(LocalContext.current, "${(loginResult as LoginResult.Failure).message}", Toast.LENGTH_SHORT).show()

            }
        }


    }
}