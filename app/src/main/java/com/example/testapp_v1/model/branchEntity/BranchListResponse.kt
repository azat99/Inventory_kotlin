package com.example.testapp_v1.model.branchEntity


import com.google.gson.annotations.SerializedName

data class BranchListResponse(
    val success: Boolean,
    @SerializedName("data")
    val data: List<BranchData>
)