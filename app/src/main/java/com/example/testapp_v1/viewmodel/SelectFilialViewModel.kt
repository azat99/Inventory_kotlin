package com.example.testapp_v1.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.testapp_v1.model.filialsEntity.Data
import com.example.testapp_v1.repositories.Repository

class SelectFilialViewModel(private val repo: Repository):ViewModel() {

    fun putTdsDataToDatabase(){
        repo.addDataToTdsData()
    }

    fun putBranchDataToDatabase(){
        repo.putBranchListToDatabase()
    }

    fun putFilialDataToDatabase(){
        repo.getFilialAndPutInDB()
    }

    fun getDataFromDatabase():LiveData<List<Data>> {
        return repo.getAllFilialList()
    }




}