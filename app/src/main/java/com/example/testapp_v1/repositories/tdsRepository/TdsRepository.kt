package com.example.testapp_v1.repositories.tdsRepository

import androidx.lifecycle.LiveData
import com.example.testapp_v1.model.detailEntity.Details
import com.example.testapp_v1.model.tdsDataEntity.TdsData

interface TdsRepository {

    suspend fun findDataFromBarcode(barcode: String): TdsData

    fun addDataToDetails(details: Details)

    fun getAllDetailsList(barcode: String): LiveData<List<Details>>

    suspend fun comparisonBarcode(barcode: String, parentID: String): List<Details>

    fun deleteAllDetailsData()

    fun commentUpdate(currentBarcode: String, currentParentID: String, comment: String)

    suspend fun getCommentFromCurrentBarcode(
        currentBarcode: String,
        currentParentID: String
    ): String

    fun updateCounter(docID: String)

    fun updateOwnerName(currentBarcode: String, name: String)

    fun checkOwnerName(currentBarcode: String,parentID: String):LiveData<Details>

}