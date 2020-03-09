package com.example.testapp_v1.model.inventories

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface InventoryDao {

    @Insert
    suspend fun addInventory(inventory: Inventory)

    @Query("Select * from inventory where arm = 0 and filialId = :currentID")
    fun getAllInventory(currentID: String): LiveData<List<Inventory>>

    @Query("Select * from inventory where arm = 1 and filialId = :currentID")
    fun getAllInventoryFromArchive(currentID: String): LiveData<List<Inventory>>

    @Query("update inventory set arm = :bool, comment = :comment where id = :currentID")
    fun updateInventory(currentID: String, comment: String, bool: Boolean)

    @Query("select * from inventory where endDateTime is null")
    fun checkLastDate(): List<Inventory>

    @Query("update inventory set endDateTime = :dateTime where id = :currentID")
    fun updateLastDate(currentID: String, dateTime: String)

    @Query("update inventory set counter = counter + 1 where id = :docID")
    fun updateCounter(docID: String)

    @Query("delete from inventory")
    fun deleteInventory()

}