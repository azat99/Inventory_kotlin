package com.example.testapp_v1.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp_v1.model.detailEntity.Details
import com.example.testapp_v1.model.tdsDataEntity.TdsData
import com.example.testapp_v1.repositories.tdsRepository.TdsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel(private val repo: TdsRepository) : ViewModel() {

    var foundLiveData = MutableLiveData<TdsData>()
    var text = ""
    var name = ""
    var comment = MutableLiveData<String>()
    var tekseru = MutableLiveData<Int>()

    private fun findDataFromBarcode(barcode: String) {
        viewModelScope.launch {
            val found = repo.findDataFromBarcode(barcode) // main
            foundLiveData.value = found
        }
    }

    fun onSearchPressedWith(text: String) {
        findDataFromBarcode(text)
    }

    fun putAllBarcodeToDetails(details: Details) {
        repo.addDataToDetails(details)
    }

    fun getAllBarcode(barcode: String): LiveData<List<Details>> = repo.getAllDetailsList(barcode)


    fun proverkaNaPodlinnost(barcode: String, parentID: String) {
        viewModelScope.launch {
            tekseru.value = null
            tekseru.value = comparisonTwoBarcode(barcode, parentID).size
        }
    }

    private suspend fun comparisonTwoBarcode(barcode: String, parentID: String): List<Details> {
        return withContext(Dispatchers.IO) { repo.comparisonBarcode(barcode, parentID) }
    }

    fun deleteAllDataFromDetails() {
        repo.deleteAllDetailsData()
    }

    fun updateCommentFromBarcode(currentBarcode: String, currentParentID: String, comment: String) {
        viewModelScope.launch {
            repo.commentUpdate(currentBarcode, currentParentID, comment)
        }
    }


    fun getCurrentComment(currentBarcode: String, currentParentID: String) {
        viewModelScope.launch {
            //comment.value = null
            val result = repo.getCommentFromCurrentBarcode(currentBarcode, currentParentID)
            comment.value = result
        }
    }

    fun updateCounter(docID: String) {
        repo.updateCounter(docID)
    }

    fun updateOwnerName(currentBarcode: String, name: String) {
        repo.updateOwnerName(currentBarcode, name)
    }

    fun checkCurrentOwnerName(currentBarcode: String, parentID: String): LiveData<Details> =
        repo.checkOwnerName(currentBarcode, parentID)

}