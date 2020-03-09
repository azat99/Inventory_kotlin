package com.example.testapp_v1.repositories.documentRepository

import androidx.lifecycle.LiveData
import com.example.testapp_v1.model.detailEntity.Details
import com.example.testapp_v1.model.inventories.Inventory

interface DocumentRepository {

    fun addDataToInventary(inventory: Inventory)

    fun getAllInventoryList(currentID: String): LiveData<List<Inventory>>

    fun updateArm(currentID: String, comment: String, bool: Boolean)

    fun deleteAllInventory()

    fun getAllInventoryArchiveList(currentID: String): LiveData<List<Inventory>>

    suspend fun checkDate(): List<Inventory>

    fun updateDate(currentID: String, date: String)

    fun sizeOfBarcode(docID: String): LiveData<List<Details>>
}