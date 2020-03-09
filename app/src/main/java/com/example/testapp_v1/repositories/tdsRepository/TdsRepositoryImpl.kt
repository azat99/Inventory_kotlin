package com.example.testapp_v1.repositories.tdsRepository

import androidx.lifecycle.LiveData
import com.exa.TdsDataDao
import com.example.testapp_v1.model.api.ApiService
import com.example.testapp_v1.model.detailEntity.Details
import com.example.testapp_v1.model.detailEntity.DetailsDao
import com.example.testapp_v1.model.inventories.InventoryDao
import com.example.testapp_v1.model.tdsDataEntity.TdsData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TdsRepositoryImpl(
    private val tdsDataDao: TdsDataDao,
    private val detailsDao: DetailsDao,
    private val inventoryDao: InventoryDao
) : TdsRepository {

    override suspend fun findDataFromBarcode(barcode: String): TdsData {
        return withContext(Dispatchers.IO) {
            tdsDataDao.getTdsDataFromBarcode(barcode)
        }
    }

    override fun addDataToDetails(details: Details) {
        GlobalScope.launch {
            detailsDao.addDataToDetails(details)
        }
    }

    override fun getAllDetailsList(barcode: String): LiveData<List<Details>> {
        return detailsDao.getBarcodeFromDetails(barcode)
    }

    override suspend fun comparisonBarcode(barcode: String, parentID: String): List<Details> {
        return withContext(Dispatchers.IO) { detailsDao.comparisonTwoBarcode(barcode, parentID) }
    }

    override fun deleteAllDetailsData() {
        GlobalScope.launch {
            detailsDao.deleteDetails()
        }
    }

    override fun commentUpdate(currentBarcode: String, currentParentID: String, comment: String) {
        GlobalScope.launch {
            detailsDao.updateDetails(currentBarcode, currentParentID, comment)
        }
    }

    override suspend fun getCommentFromCurrentBarcode(
        currentBarcode: String,
        currentParentID: String
    ): String {
        return withContext(Dispatchers.IO) {
            detailsDao.getCommentFromCurrentBarcode(
                currentBarcode,
                currentParentID
            )
        }
    }

    override fun updateCounter(docID: String) {
        GlobalScope.launch {
            inventoryDao.updateCounter(docID)
        }
    }

    override fun updateOwnerName(currentBarcode: String, name: String) {
        GlobalScope.launch {
            detailsDao.updateOwnerName(currentBarcode, name)
        }
    }

    override fun checkOwnerName(currentBarcode: String,parentID: String): LiveData<Details> {
        return detailsDao.checkOwnerName(currentBarcode,parentID)
    }

}