package com.example.shipmentdemoapp.presentaion.ui.composables

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shipmentdemoapp.R
import com.example.shipmentdemoapp.presentaion.viewmodel.LoginViewModel

@Composable
fun LoginScreen(loginViewModel:LoginViewModel= hiltViewModel(), onClickReigster: () -> Unit) {
    val phoneState by loginViewModel.phoneNumber.collectAsState()
    val passwordState by loginViewModel.password.collectAsState()
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
        }) {
            Text(text = stringResource(R.string.login))
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