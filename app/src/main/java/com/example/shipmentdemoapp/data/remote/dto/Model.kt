package com.example.shipmentdemoapp.data.remote.dto

data class Model(
    val auth: Auth,
    val event: List<Event>,
    val info: Info,
    val item: List<Item>,
    val variable: List<Variable>
)