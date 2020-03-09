package com.example.testapp_v1.model.detailEntity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomWarnings

@Entity
class Details(
    val p_id :String,
    val barcode: String,
    val comment:String?,
    val name:String?
){
    @PrimaryKey(autoGenerate = true)
    var id = 0
}