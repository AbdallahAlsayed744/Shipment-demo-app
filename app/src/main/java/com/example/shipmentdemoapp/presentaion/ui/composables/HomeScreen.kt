package com.example.shipmentdemoapp.presentaion.ui.composables

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ShoppingCart
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
    token: String,
    userId: String,
    onShippingQuotationClick: (String) -> Unit,
) {

    val shipments by homeViewModel.shipments.collectAsState()
    val isLoading by homeViewModel.isLoading.collectAsState()
    val errorMessage by homeViewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        homeViewModel.getShipments(token, page = 1)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.primary,
                    onClick = {
                        onShippingQuotationClick(token)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ShoppingCart,
                        contentDescription = "Add Icon",
                        tint = Color.White
                    )
                }
            },
        ) {
            LazyColumn(modifier = Modifier.padding(top = it.calculateTopPadding())) {
                if (isLoading) {
                    item {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    }
                }

                errorMessage?.let {
                    item {
                        Text("Error: $it", color = MaterialTheme.colorScheme.error, modifier = Modifier.align(Alignment.CenterHorizontally))
                    }
                }

                items(shipments) { shipment ->
                    ShipmentItem(shipment = shipment)
                }
            }
        }
    }
}

@Composable
fun ShipmentItem(shipment: Shipment) {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text("Shipment Name: ${shipment.name}", style = MaterialTheme.typography.bodyMedium)
        Text("Description: ${shipment.description}", style = MaterialTheme.typography.bodyMedium)

    }
}