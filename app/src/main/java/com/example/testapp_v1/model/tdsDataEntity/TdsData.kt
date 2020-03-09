package com.example.testapp_v1.model.tdsDataEntity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class TdsData(
    val num: String?,
    @PrimaryKey
    val barcode: String,
    val name: String?,
    val unitName: String?,
    val owner: String?
)