package com.example.shipmentdemoapp.presentaion.ui.composables

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.shipmentdemoapp.R
import com.example.shipmentdemoapp.presentaion.RegisterState
import com.example.shipmentdemoapp.presentaion.viewmodel.RegisterViewModel
import java.io.File
import java.io.FileOutputStream


@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel= hiltViewModel()
) {

    val registrationState by viewModel.registrationState.collectAsState()
    val countriesState by viewModel.countriesState.collectAsState()

    val name by viewModel.name.collectAsState()
    val email by viewModel.email.collectAsState()
    val phone by viewModel.phone.collectAsState()
    val password by viewModel.password.collectAsState()


//    val countryList: List<String> = listOf(
//        "Egypt",
//        "KSA",
//        "England"
//    )

    var selectedCountry by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    var selectedFile by remember { mutableStateOf<File?>(null) }

    if (countriesState is RegisterState.Loading) {
        CircularProgressIndicator()
    }

    val countryList = when(val state = countriesState) {
        is RegisterState.SuccessCountries -> state.countries
        is RegisterState.Failure -> {

            emptyList()
        }
        else -> emptyList()


    }





    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
       ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = name,
            onValueChange = {
              viewModel.setName(it)
            },
            label = { Text(text = stringResource(id = R.string.name)) },
            modifier = Modifier.fillMaxWidth()
        )


        OutlinedTextField(
            value = email,
            onValueChange = {
               viewModel.setEmail(it)
            },
            label = { Text(text = stringResource(id = R.string.email)) },
            modifier = Modifier.fillMaxWidth()
        )


        OutlinedTextField(
            value = phone,
            onValueChange = {
                viewModel.setPhone(it)
            },
            label = { Text(text = stringResource(id = R.string.phone)) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )

        // Password
        OutlinedTextField(
            value = password,
            onValueChange = {
                viewModel.setPassword(it)
            },
            label = { Text(stringResource(id = R.string.password)) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))


        Button(
            onClick = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {

            Text(
                text = selectedCountry.takeIf { it.isNotEmpty() } ?: "Select Country",
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown Arrow",
                modifier = Modifier.size(24.dp)
            )
        }

        // Dropdown Menu for selecting country
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            countryList.forEach { country ->
                DropdownMenuItem(
                    text = { Text(text = country) },
                    onClick = {
                        selectedCountry = country
                        expanded = false
                    }
                )
            }
        }
        // Image Picker
       ImagePickerScreen(
           onFileSelected = { file ->
               selectedFile = file
           }
       )

        Spacer(modifier = Modifier.height(20.dp))

        // Register Button
        Button(
            onClick = {
                selectedFile?.let { file ->
                    Log.d("RegisterScreenfile", "file: $file")
                    viewModel.register(name, email, phone, password, selectedCountry, file)
                }
//                onRegisterClick(name, email, phone, password, countryList.indexOf(selectedCountry), imageUri)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.register))
        }

        when (registrationState) {
            is RegisterState.Loading -> CircularProgressIndicator()
            is RegisterState.Success -> Text("Registration Successful!")
            is RegisterState.Failure ->
                Text("Error: " +
                    "${(registrationState as RegisterState.Failure).error}"

            )

            else -> Unit
        }


    }



}


@Composable
fun ImagePickerScreen(onFileSelected: (File) -> Unit) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                val file = getFileFromUri(context, uri) // Use the context here
                if (file != null) {
                    onFileSelected(file)
                    selectedImageUri = uri
                }else {
                    Log.e("ImagePicker", "Failed to retrieve file from URI")
                }
            }
        }
    )

    Column(
        modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                launcher.launch("image/*")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Select Profile Image")
        }

        Spacer(modifier = Modifier.height(16.dp))

        selectedImageUri?.let { uri ->
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = "Selected Image",
                modifier = Modifier
                    .size(200.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}

fun getFileFromUri(context: Context, uri: Uri): File? {
    return try {
        // Open InputStream from the content URI
        val inputStream = context.contentResolver.openInputStream(uri)

        // Create a temporary file in the cache directory
        val tempFile = File.createTempFile("image_picker", ".jpg", context.cacheDir)

        // Copy the contents of the InputStream into the temporary file
        inputStream?.use { input ->
            tempFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        tempFile // Return the created temporary file
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
