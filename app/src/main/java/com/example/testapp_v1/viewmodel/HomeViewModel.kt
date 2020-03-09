package com.example.testapp_v1.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.testapp_v1.model.detailEntity.Details
import com.example.testapp_v1.model.inventories.Inventory
import com.example.testapp_v1.repositories.documentRepository.DocumentRepository

class HomeViewModel(private val repo: DocumentRepository) : ViewModel() {

    fun putAllDataToDatabase(inventory: Inventory) {
        repo.addDataToInventary(inventory)
    }

    fun getAllListFromInventory(currentID: String): LiveData<List<Inventory>> = repo.getAllInventoryList(currentID)

    fun getAllArchiveListFromInventory(currentID: String): LiveData<List<Inventory>> =
        repo.getAllInventoryArchiveList(currentID)

    fun deleteAllDataFromDatabase() {
        repo.deleteAllInventory()
    }

    fun updateArmFromInventory(currentID: String, comment: String, boolean: Boolean) {
        repo.updateArm(currentID, comment, boolean)
    }

    fun updateEndDate(currentID: String,date:String){
        repo.updateDate(currentID,date)
    }

    fun getBarcodeSize(docID:String):LiveData<List<Details>>{
        return repo.sizeOfBarcode(docID)
    }
}