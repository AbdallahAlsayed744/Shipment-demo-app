package com.example.shipmentdemoapp.data.remote.dto

data class Body(
    val formdata: List<Formdata>,
    val mode: String,
    val options: Options,
    val raw: String
)