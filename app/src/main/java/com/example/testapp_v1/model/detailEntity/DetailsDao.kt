package com.example.testapp_v1.model.detailEntity

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDataToDetails(details: Details)

    @Query("select * from details where barcode = :barcode and p_id = :parentID")
    fun comparisonTwoBarcode(barcode:String,parentID: String): List<Details>

    @Query("select * from details where p_id = :parentID")
    fun getBarcodeFromDetails(parentID : String): LiveData<List<Details>>

    @Query("update details set comment = :comment where barcode = :currentBarcode and p_id = :currentParentID")
    fun updateDetails(currentBarcode:String, currentParentID:String, comment:String)

    @Query("select comment from details where barcode = :currentBarcode and p_id = :currentParentID")
    fun getCommentFromCurrentBarcode(currentBarcode: String,currentParentID: String):String

    @Query("update details set name = :ownerName where barcode = :currentBarcode")
    fun updateOwnerName(currentBarcode: String,ownerName:String)

    @Query("select * from details where barcode = :currentBarcode and p_id = :currentParentID")
    fun checkOwnerName(currentBarcode: String,currentParentID: String):LiveData<Details>

    @Query("delete from details")
    fun deleteDetails()

}