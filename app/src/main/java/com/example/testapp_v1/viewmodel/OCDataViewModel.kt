package com.example.testapp_v1.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp_v1.model.tdsDataEntity.TdsData
import com.example.testapp_v1.repositories.tdsRepository.TdsRepository
import kotlinx.coroutines.launch

class OCDataViewModel(val repo: TdsRepository): ViewModel() {

    var foundLiveData = MutableLiveData<TdsData>()

    private fun findDataFromBarcode(barcode: String) {
        viewModelScope.launch {
            val found = repo.findDataFromBarcode(barcode) // main
            foundLiveData.value = found
        }
    }

    fun onSearchPressedWith(text: String) {
        findDataFromBarcode(text)
    }
}