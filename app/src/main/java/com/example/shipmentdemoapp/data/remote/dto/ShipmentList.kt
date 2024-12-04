package com.example.shipmentdemoapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ShipmentList(
    @SerializedName("total") val total: Int,
    @SerializedName("per_page") val perPage: Int,
    @SerializedName("current_page") val currentPage: Int,
    @SerializedName("last_page") val lastPage: Int,
    @SerializedName("next_page_url") val nextPageUrl: String?,
    @SerializedName("prev_page_url") val prevPageUrl: String?,
    @SerializedName("from") val from: Int?,
    @SerializedName("to") val to: Int?,
    @SerializedName("data") val data: List<Shipment>
)

