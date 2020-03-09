package com.example.testapp_v1.repositories.documentRepository

import androidx.lifecycle.LiveData
import com.example.testapp_v1.model.detailEntity.Details
import com.example.testapp_v1.model.detailEntity.DetailsDao
import com.example.testapp_v1.model.inventories.Inventory
import com.example.testapp_v1.model.inventories.InventoryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DocumentRepositoryImpl(
    private val inventoryDao: InventoryDao,
    private val detailsDao: DetailsDao
) : DocumentRepository {


    override fun addDataToInventary(inventory: Inventory) {
        GlobalScope.launch(Dispatchers.IO) {
            inventoryDao.addInventory(inventory)
        }
    }

    override fun getAllInventoryList(currentID: String): LiveData<List<Inventory>> {
        return inventoryDao.getAllInventory(currentID)
    }

    override fun updateArm(currentID: String, comment: String, bool: Boolean) {
        GlobalScope.launch {
            inventoryDao.updateInventory(currentID, comment, bool)
        }
    }

    override fun deleteAllInventory() {
        GlobalScope.launch(Dispatchers.IO) {
            inventoryDao.deleteInventory()
        }
    }

    override fun getAllInventoryArchiveList(currentID: String): LiveData<List<Inventory>> {
        return inventoryDao.getAllInventoryFromArchive(currentID)
    }

    override suspend fun checkDate(): List<Inventory> {
        return withContext(Dispatchers.IO) { inventoryDao.checkLastDate() }
    }

    override fun updateDate(currentID: String, date: String) {
        GlobalScope.launch {
            inventoryDao.updateLastDate(currentID, date)
        }
    }

    override fun sizeOfBarcode(docID: String): LiveData<List<Details>> {
        return detailsDao.getBarcodeFromDetails(docID)
    }


}