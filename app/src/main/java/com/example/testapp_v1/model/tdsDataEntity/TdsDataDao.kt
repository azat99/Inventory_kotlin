package com.exa

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testapp_v1.model.branchEntity.BranchData
import com.example.testapp_v1.model.tdsDataEntity.TdsData

@Dao
interface TdsDataDao {

    @Query("select * from tdsdata")
    fun getAllTdsData(): List<TdsData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTdsData(dataList: List<TdsData>)

    @Query("delete from tdsdata")
    fun deleteAllTdsData()

    @Query("select * from tdsdata where barcode = :barcode")
    suspend fun getTdsDataFromBarcode(barcode: String): TdsData

}