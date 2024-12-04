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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shipmentdemoapp.R
import com.example.shipmentdemoapp.presentaion.utl.LoginResult
import com.example.shipmentdemoapp.presentaion.viewmodel.LoginViewModel

@Composable
fun LoginScreen(loginViewModel:LoginViewModel= hiltViewModel(), onClickReigster: () -> Unit,onClickHome: (String, String) -> Unit) {
    val phoneState by loginViewModel.phoneNumber.collectAsState()
    val passwordState by loginViewModel.password.collectAsState()
    val loginResult by loginViewModel.loginResult.collectAsState()
    val showToast by loginViewModel.showToast.collectAsState()

    var phoneError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    val context = LocalContext.current

    LaunchedEffect(showToast) {
        if (showToast) {
            Toast.makeText(
                context,
                "Phone number or password cannot be empty!",
                Toast.LENGTH_SHORT
            ).show()
            loginViewModel.setShowToast(false)
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
            isError = phoneError,
            onValueChange = {
               loginViewModel.setPhoneNumber(it)
                phoneError = false

            },
            label = {
                Text(text = stringResource(R.string.phone))
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            maxLines = 1
        )
        if (phoneError) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                textAlign = TextAlign.Start,
                fontSize = 14.sp,
                text = "Phone number cannot be empty!",
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier =Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = passwordState,
            onValueChange = {
                 loginViewModel.setPassword(it)
                passwordError = false

            },
            label = {
                Text(text = stringResource(R.string.password))
            },
            isError = passwordError,
            visualTransformation = PasswordVisualTransformation(),
            maxLines = 1
        )
        if (passwordError) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                textAlign = TextAlign.Start,
                fontSize = 14.sp,
                text = "Password cannot be empty!",
                color = MaterialTheme.colorScheme.error
            )
        }


        Spacer(modifier =Modifier.height(10.dp))



        Button(modifier = Modifier, shape = RoundedCornerShape(3.dp),onClick = {
//            viewModel.login(phone, password) {
//                onLoginSuccess()
//            }
            phoneError = phoneState.isEmpty()
            passwordError = passwordState.isEmpty()
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
                val response = (loginResult as LoginResult.Success).data
                val accessToken = response.access_token
                val userId = response.user.id.toString()

                onClickHome(accessToken,userId)
            }

            is LoginResult.Failure -> {
                LaunchedEffect(loginResult) {
                    Toast.makeText(
                        context,
                        "Invalid phone number or password!"
                        ,

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


        }



}