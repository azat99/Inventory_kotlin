package com.example.testapp_v1.model.filialsEntity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Data(
    @PrimaryKey
    val id: String,
    val name: String
)