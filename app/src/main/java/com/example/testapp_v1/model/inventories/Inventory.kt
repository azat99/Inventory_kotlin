package com.example.testapp_v1.model.inventories

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Inventory(
    val docName:String,
    val dateTime: String,
    val filialName:String,
    val filialId:String,
    val arm:Boolean,
    val endDateTime:String?,
    val comment:String?,
    val counter:Int
){
    @PrimaryKey(autoGenerate = true)
    var id = 0
}