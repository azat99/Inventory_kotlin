package com.example.testapp_v1.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import com.exa.TdsDataDao
import com.example.testapp_v1.model.api.ApiService
import com.example.testapp_v1.model.branchEntity.BranchData
import com.example.testapp_v1.model.branchEntity.BranchDataDao
import com.example.testapp_v1.model.filialsEntity.Data
import com.example.testapp_v1.model.filialsEntity.DataDao
import com.example.testapp_v1.model.tdsDataEntity.TdsData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RepositoryImpl(
    private val dataDao: DataDao,
    private val branchDataDao: BranchDataDao,
    private val tdsDataDao: TdsDataDao,
    private val apiService: ApiService
) : Repository {

    override fun addDataToTdsData() {
        try {
            GlobalScope.launch {
                val result = apiService.getTdsList().await()
               // tdsDataDao.deleteAllTdsData()
                tdsDataDao.insertAllTdsData(result.data)

            }
        }catch (e:Exception){
            Log.d("azazazaza",e.printStackTrace().toString())
        }

    }

    override fun getAllDataFromTds(): List<TdsData> {
        return tdsDataDao.getAllTdsData()
    }

    override fun getFilialAndPutInDB() {

        try {
            GlobalScope.launch(Dispatchers.IO) {
                val result = apiService.getFilialList().await()
               // dataDao.deleteAllData()
                dataDao.insertAllData(result.data)
            }
        } catch (e: Exception) {
            Log.e("azazazaza",e.printStackTrace().toString())
        }

    }

    override fun getAllFilialList(): LiveData<List<Data>> {
        return dataDao.getAllData()
    }


    override fun putBranchListToDatabase() {
        try {
            GlobalScope.launch(Dispatchers.IO) {
                val result = apiService.getBranchList().await()
                // branchDataDao.deleteAllBranchData()
                branchDataDao.insertAllBranchData(result.data)
            }
        }catch (e: Exception) {
            Log.e("azazazaza",e.printStackTrace().toString())
        }

    }

    override fun getAllBranchListData(): LiveData<List<BranchData>> {
        return branchDataDao.getAllBranchData()
    }


}