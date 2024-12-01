package com.example.shipmentdemoapp.presentaion.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.shipmentdemoapp.R

@Composable
fun RegisterScreen() {

    val countryList: List<String> = listOf(
        "Egypt",
        "KSA",
        "England"
    )

    var selectedCountry by remember { mutableStateOf(countryList.first()) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
        , verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Name
        OutlinedTextField(
            value = "",
            onValueChange = {  },
            label = { Text(text = stringResource(id = R.string.name)) },
            modifier = Modifier.fillMaxWidth()
        )

        // Email
        OutlinedTextField(
            value = "",
            onValueChange = { },
            label = { Text(text = stringResource(id = R.string.email)) },
            modifier = Modifier.fillMaxWidth()
        )

        // Phone
        OutlinedTextField(
            value = "",
            onValueChange = { },
            label = { Text(text = stringResource(id = R.string.phone)) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )

        // Password
        OutlinedTextField(
            value = "",
            onValueChange = { },
            label = { Text(stringResource(id = R.string.password)) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        // Country Dropdown
        DropdownMenu(
            expanded = true,
            onDismissRequest = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            countryList.forEach { country ->
                DropdownMenuItem(text = {
                     Text(text = country)
                }, onClick = {
                    selectedCountry=country
                })
            }
        }

        // Image Picker
        Button(
            onClick = {  },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Select Profile Image")
        }

        // Image preview
//        imageUri?.let {
            Image(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "Profile Image", modifier = Modifier.size(100.dp))
//        }

        Spacer(modifier = Modifier.height(24.dp))

        // Register Button
        Button(
            onClick = {
//                onRegisterClick(name, email, phone, password, countryList.indexOf(selectedCountry), imageUri)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.register))
        }
    }



}