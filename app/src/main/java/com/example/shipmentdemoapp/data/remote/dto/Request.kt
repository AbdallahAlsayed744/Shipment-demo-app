package com.example.shipmentdemoapp.data.remote.dto

data class Request(
    val auth: Auth,
    val body: Body,
    val header: List<Header>,
    val method: String,
    val url: Url
)