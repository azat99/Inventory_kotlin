package com.example.testapp_v1.model.filialsEntity

import com.google.gson.annotations.SerializedName


data class FilialDataResponse(
    val success: Boolean,
    @SerializedName("data")
    val data: List<Data>
)