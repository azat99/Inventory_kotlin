package com.example.testapp_v1.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testapp_v1.model.api.ApiService
import com.example.testapp_v1.model.branchEntity.BranchData
import com.example.testapp_v1.model.filialsEntity.Data
import com.example.testapp_v1.model.inventories.Inventory
import com.example.testapp_v1.model.tdsDataEntity.TdsData

interface Repository {

    fun getFilialAndPutInDB()

    fun getAllFilialList():LiveData<List<Data>>

    fun putBranchListToDatabase()

    fun getAllBranchListData():LiveData<List<BranchData>>

    fun addDataToTdsData()

    fun getAllDataFromTds(): List<TdsData>

}