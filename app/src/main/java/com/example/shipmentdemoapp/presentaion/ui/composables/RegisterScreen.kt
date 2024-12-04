package com.example.shipmentdemoapp.presentaion.ui.composables

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.shipmentdemoapp.R
import com.example.shipmentdemoapp.data.remote.dto.Country
import com.example.shipmentdemoapp.presentaion.utl.RegisterState
import com.example.shipmentdemoapp.presentaion.viewmodel.RegisterViewModel
import java.io.File


@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onLoginClick: () -> Unit
) {
    val registrationState by viewModel.registrationState.collectAsState()
    val countriesState by viewModel.countriesState.collectAsState()

    val name by viewModel.name.collectAsState()
    val email by viewModel.email.collectAsState()
    val phone by viewModel.phone.collectAsState()
    val password by viewModel.password.collectAsState()

    val context = LocalContext.current

    var selectedCountry by remember { mutableStateOf<Country?>(null) }
    var expanded by remember { mutableStateOf(false) }
    var selectedFile by remember { mutableStateOf<File?>(null) }


    var nameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var phoneError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var countryError by remember { mutableStateOf(false) }
    var fileError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = name,
            onValueChange = {
                viewModel.setName(it)
                nameError = false
            },
            label = { Text(text = "Name") },
            isError = nameError,
            modifier = Modifier.fillMaxWidth()
        )
        if (nameError) Text("Name is required",modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            textAlign = TextAlign.Start,
            fontSize = 14.sp, color = MaterialTheme.colorScheme.error)

        Spacer(modifier = Modifier.height(8.dp))


        OutlinedTextField(
            value = email,
            onValueChange = {
                viewModel.setEmail(it)
                emailError = false
            },
            label = { Text(text = "Email") },
            isError = emailError,
            modifier = Modifier.fillMaxWidth()
        )
        if (emailError) Text("Email is required",modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            textAlign = TextAlign.Start,
            fontSize = 14.sp,color = MaterialTheme.colorScheme.error)

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = {
                viewModel.setPhone(it)
                phoneError = false
            },
            label = { Text(text = "Phone") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
            isError = phoneError,
            modifier = Modifier.fillMaxWidth()
        )
        if (phoneError) Text("Phone number is required", modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            textAlign = TextAlign.Start,
            fontSize = 14.sp,color = MaterialTheme.colorScheme.error)

        Spacer(modifier = Modifier.height(8.dp))


        OutlinedTextField(
            value = password,
            onValueChange = {
                viewModel.setPassword(it)
                passwordError = false
            },
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation(),
            isError = passwordError,
            modifier = Modifier.fillMaxWidth()
        )
        if (passwordError) Text("Password is required", modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            textAlign = TextAlign.Start,
            fontSize = 14.sp,color = MaterialTheme.colorScheme.error)

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = selectedCountry?.name ?: "Select Country",
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown Arrow"
            )
        }
        if (countryError) Text("Please select a country", modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            textAlign = TextAlign.Start,
            fontSize = 14.sp,color = MaterialTheme.colorScheme.error)

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            countriesState.forEach { country ->
                DropdownMenuItem(
                    text = { Text(text = country.name) },
                    onClick = {
                        selectedCountry = country
                        countryError = false
                        expanded = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        ImagePickerScreen(
            onFileSelected = { file ->
                selectedFile = file
                fileError = false
            },
            fileError = fileError
        )

        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = {

                nameError = name.isEmpty()
                emailError = email.isEmpty()
                phoneError = phone.isEmpty()
                passwordError = password.isEmpty()
                countryError = selectedCountry == null
                fileError = selectedFile == null

                if (!nameError && !emailError && !phoneError && !passwordError && !countryError && !fileError) {
                    selectedFile?.let { file ->
                        viewModel.register(
                            name,
                            email,
                            phone,
                            password,
                            selectedCountry!!.id.toString(),
                            "",
                            file,
                            ""
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register")
        }

        when (registrationState) {
            is RegisterState.Loading -> CircularProgressIndicator()
            is RegisterState.Success -> {
                Text("Registration Successful!")
                onLoginClick()
            }
            is RegisterState.Failure -> Text(
                "Error: ${(registrationState as RegisterState.Failure).error}",
                color = MaterialTheme.colorScheme.error
            )
            else -> Unit
        }
    }
}

@Composable
fun ImagePickerScreen(
    onFileSelected: (File) -> Unit,
    fileError: Boolean
) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                val file = getFileFromUri(context, uri)
                if (file != null) {
                    onFileSelected(file)
                    selectedImageUri = uri
                } else {
                    Toast.makeText(context, "Failed to retrieve image", Toast.LENGTH_SHORT).show()
                }
            }
        }
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = { launcher.launch("image/*") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Pick an Image")
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (fileError) Text("Image is required",modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            textAlign = TextAlign.Start,
            fontSize = 14.sp, color = MaterialTheme.colorScheme.error)

        selectedImageUri?.let { uri ->
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = "Selected Image",
                modifier = Modifier
                    .size(150.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}

fun getFileFromUri(context: Context, uri: Uri): File? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val tempFile = File.createTempFile("image_picker", ".jpg", context.cacheDir)
        inputStream?.use { input ->
            tempFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        tempFile
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

