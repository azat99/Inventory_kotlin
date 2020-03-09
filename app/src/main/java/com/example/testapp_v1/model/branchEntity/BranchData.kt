package com.example.testapp_v1.model.branchEntity


import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class BranchData(
    @PrimaryKey
    val id: String,
    val name: String,
    val firmId: String
)