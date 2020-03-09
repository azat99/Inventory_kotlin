package com.example.testapp_v1.model.filialsEntity

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testapp_v1.model.filialsEntity.Data

@Dao
interface DataDao {

    @Query("select * from data")
    fun getAllData(): LiveData<List<Data>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllData(dataList:List<Data>)

    @Query("delete from data")
    fun deleteAllData()

}