package com.example.shipmentdemoapp.presentaion.ui.composables

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shipmentdemoapp.data.local.TokenManager
import com.example.shipmentdemoapp.data.local.UserManager
import com.example.shipmentdemoapp.presentaion.viewmodel.ShipmentQuotationViewModel
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

@Composable
fun ShipmentQuotationScreen(
    shipViewModel: ShipmentQuotationViewModel = hiltViewModel(),
    token:String,
    onSubmitSuccess: () -> Unit // Callback for successful submission
) {
    val shipmentName by shipViewModel.shipmentName.collectAsState()
    val description by shipViewModel.shipmentDescription.collectAsState()
    val quantity by shipViewModel.shipmentQuntatiy.collectAsState()
    val comment by shipViewModel.shipmentcomment.collectAsState()
    val isLoading by shipViewModel.isLoading.collectAsState()
    val errorMessage by shipViewModel.errorMessage.collectAsState()

    val containers = remember { mutableStateListOf<Container>() } // List of containers






    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()), // Enable scrolling for the screen
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Shipment Name Input
        OutlinedTextField(
            value = shipmentName,
            onValueChange = { shipViewModel.setShipmentName(it) },
            label = { Text("Shipment Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Description Input
        OutlinedTextField(
            value = description,
            onValueChange = { shipViewModel.setShipmentDescription(it) },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Quantity Input
        OutlinedTextField(
            value = quantity,
            onValueChange = { shipViewModel.setShipmentQuntatiy(it) },
            label = { Text("Quantity") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Containers Section
        Text("Containers", style = MaterialTheme.typography.headlineSmall)
        if (containers.isEmpty()) {
            Button(
                onClick = { containers.add(Container("", "", "", "")) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Container")
            }
        } else {
            containers.forEachIndexed { index, container ->
                ContainerInput(
                    container = container,
                    isLast = index == containers.lastIndex,
                    onRemove = { containers.removeAt(index) },
                    onAdd = { containers.add(Container("", "", "", "")) },
                    onUpdate = { updatedContainer -> containers[index] = updatedContainer }
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Comment Input
        OutlinedTextField(
            value = comment,
            onValueChange = { shipViewModel.setShipmentcomment(it) },
            label = { Text("Comment") },
            modifier = Modifier.fillMaxWidth()
        )

//        Spacer(modifier = Modifier.height(20.dp))

        // Submit Button
        Button(
            onClick = {
                val quantityInt = quantity.toIntOrNull() ?: 0
                if (containers.isEmpty()) {
                    shipViewModel.setErrorMessage("Please add at least one container.")
                } else {
                    shipViewModel.submitShipmentQuotation(

                        containers = containers,
                        quantity = quantityInt,
                        mytoken = token,
                        onSuccess = onSubmitSuccess
                    )
                    Log.d("Shipmentoken", "token: ${token}")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading) {
                Text("Submitting...")
            } else {
                Text("Submit")
            }
        }

        // Error Message Display
        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(10.dp))
            Text("Error: $errorMessage", color = MaterialTheme.colorScheme.error)
        }
    }
}

@Composable
fun ContainerInput(
    container: Container,
    isLast: Boolean,
    onRemove: () -> Unit,
    onAdd: () -> Unit,
    onUpdate: (Container) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = container.number,
            onValueChange = { onUpdate(container.copy(number = it)) },
            label = { Text("Container Number") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = container.size,
            onValueChange = { onUpdate(container.copy(size = it)) },
            label = { Text("Size") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = container.weight,
            onValueChange = { onUpdate(container.copy(weight = it)) },
            label = { Text("Weight") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = container.dimension,
            onValueChange = { onUpdate(container.copy(dimension = it)) },
            label = { Text("Dimension") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Row for Add and Remove Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onRemove,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp)
            ) {
                Text("Remove Container")
            }

            if (isLast) {
                Button(
                    onClick = onAdd,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp)
                ) {
                    Text("Add Container")
                }
            }
        }

//        Spacer(modifier = Modifier.height(10.dp))
    }
}


data class Container(
    val number: String,
    val size: String,
    val weight: String,
    val dimension: String
)

