package com.example.shipmentdemoapp.presentaion.ui.composables

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shipmentdemoapp.data.remote.dto.DetailsContainer
import com.example.shipmentdemoapp.data.remote.dto.ShipmentDetailsResponse
import com.example.shipmentdemoapp.presentaion.viewmodel.ShipmentDetailsViewModel

@Composable
fun ShipmentDetailsScreen(
    shipmentId: String,
    token: String,
    viewModel: ShipmentDetailsViewModel = hiltViewModel()
) {
    val shipmentDetails by viewModel.shipmentDetails.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(shipmentId) {
        viewModel.fetchShipmentDetails("Bearer $token", shipmentId)
        Log.d("ShipmentDetailsScreen", "Shipment ID: $shipmentId")
        Log.d("ShipmentDetailsScreen", "Token: $token")
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.Center)
            )
        } else if (errorMessage != null) {
            Text(
                "Error: $errorMessage",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.Center)
            )
        } else if (shipmentDetails != null) {
            ShipmentDetailsContent(details = shipmentDetails!!)
        }
    }
}

@Composable
fun ShipmentDetailsContent(details: ShipmentDetailsResponse) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {

        Card(
            modifier = Modifier.padding(top = 26.dp).fillMaxWidth(),

            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Shipment Name",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = details.shippmentDetails.shipment_name,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Description",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = details.shippmentDetails.description,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Comment",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = details.shippmentDetails.comment.ifEmpty { "No comment" },
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Status",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = details.shippmentDetails.status,
                    style = MaterialTheme.typography.bodyMedium,
                    color = getStatusColor(details.shippmentDetails.status)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Containers:",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(details.shippmentDetails.Containers) { container ->
                ContainerItem(container = container)
            }
        }
    }
}

@Composable
fun ContainerItem(container: DetailsContainer) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(8.dp)

    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Container Number: ${container.container_number}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Size: ${container.size}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Weight: ${container.weight.ifEmpty { "Not provided" }}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Dimension: ${container.dimension.ifEmpty { "Not provided" }}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun getStatusColor(status: String): Color {
    return when (status.lowercase()) {
        "pending" -> MaterialTheme.colorScheme.secondary
        "completed" -> MaterialTheme.colorScheme.primary
        "shipped" -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.onSurface
    }
}
