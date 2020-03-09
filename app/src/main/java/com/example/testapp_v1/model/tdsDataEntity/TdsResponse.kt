package com.example.testapp_v1.model.tdsDataEntity


import com.google.gson.annotations.SerializedName

data class TdsResponse(
    val success: Boolean,
    @SerializedName("data")
    val data: List<TdsData>
)