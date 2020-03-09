package com.example.testapp_v1.model.branchEntity

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testapp_v1.model.branchEntity.BranchData
import com.example.testapp_v1.model.filialsEntity.Data

@Dao
interface BranchDataDao {

    @Query("select * from branchdata")
    fun getAllBranchData(): LiveData<List<BranchData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllBranchData(dataList: List<BranchData>)

    @Query("delete from branchdata")
    fun deleteAllBranchData()


}