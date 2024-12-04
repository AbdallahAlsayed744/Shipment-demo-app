package com.example.shipmentdemoapp.presentaion.ui.composables

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shipmentdemoapp.data.remote.dto.Shipment
import com.example.shipmentdemoapp.presentaion.LoginResult
import com.example.shipmentdemoapp.presentaion.viewmodel.HomeViewModel
import com.example.shipmentdemoapp.presentaion.viewmodel.LoginViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    userId: String,
    token: String,
    onShippingQuotationClick: (String) -> Unit,
    onShipDetailsScreen: (String) -> Unit
) {
    val shipments by homeViewModel.shipments.collectAsState()
    val isLoading by homeViewModel.isLoading.collectAsState()
    val errorMessage by homeViewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit){
        homeViewModel.getShipments("Bearer $token",1)
        Log.d("HomeScreen", "Token: $token")


    }


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.secondary,
                onClick = { onShippingQuotationClick(token) }
            ) {
                Icon(
                    imageVector = Icons.Outlined.ShoppingCart,
                    contentDescription = "Request Quotation",
                    tint = Color.White
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(), // Fills the entire screen
                        contentAlignment = Alignment.Center // Centers the content inside the box
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(40.dp), // Smaller size for the progress indicator
                            color = MaterialTheme.colorScheme.primary // Set the primary color
                        )
                    }
                }
                errorMessage != null -> {
                    Text(
                        text = "Error: $errorMessage",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
                shipments.isEmpty() -> {
                    Box (
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No shipments available",
                            modifier = Modifier

//                            .padding(top = 32.dp)

                        )

                    }

                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(shipments) { shipment ->
                            ShipmentItem(shipment = shipment){
                                onShipDetailsScreen(it)

                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShipmentItem(shipment: Shipment, onShipDetailsScreen: (String) -> Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp).clickable {
               onShipDetailsScreen(shipment.id.toString())
            },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),// Optional: Customize the shape
        elevation = CardDefaults.cardElevation(8.dp) // Optional: Customize the elevation
        ) {
        Column(
            modifier = Modifier.padding(16.dp) // Padding inside the card
        ) {
            // Row for Shipment Name and ID
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween // Distributes space evenly
            ) {
                Text(
                    text = "Name: ${shipment.shipmentName}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "ID: ${shipment.id}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.padding(top = 8.dp))

            // Column for Description
            Text(
                text = "Description: ${shipment.description}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}