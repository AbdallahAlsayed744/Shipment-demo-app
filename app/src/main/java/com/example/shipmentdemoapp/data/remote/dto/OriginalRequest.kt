package com.example.shipmentdemoapp.data.remote.dto

data class OriginalRequest(
    val body: Body,
    val header: List<Header>,
    val method: String,
    val url: Url
)