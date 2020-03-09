package com.example.testapp_v1.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp_v1.model.filialsEntity.Data
import com.example.testapp_v1.model.inventories.Inventory
import com.example.testapp_v1.model.tdsDataEntity.TdsData
import com.example.testapp_v1.repositories.Repository
import com.example.testapp_v1.repositories.documentRepository.DocumentRepository
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class SettingsViewModel(
    private val repo: Repository,
    private val docRepo:DocumentRepository
): ViewModel() {

    fun  putDataToBranchDatabase(){
        repo.putBranchListToDatabase()
    }
    fun  putDataToFilialDatabase(){
        repo.getFilialAndPutInDB()
    }

    fun putDataToTdsDatabase(){
        repo.addDataToTdsData()
    }

    fun getAllFilialList():LiveData<List<Data>>{
        return repo.getAllFilialList()
    }


    var foundLiveData = MutableLiveData<List<Inventory>>()

    fun checkEndDate(){
        viewModelScope.launch{
            foundLiveData.value = docRepo.checkDate()
        }
    }

}
