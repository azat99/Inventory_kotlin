package com.example.testapp_v1.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.exa.TdsDataDao
import com.example.testapp_v1.model.branchEntity.BranchData
import com.example.testapp_v1.model.branchEntity.BranchDataDao
import com.example.testapp_v1.model.detailEntity.Details
import com.example.testapp_v1.model.detailEntity.DetailsDao
import com.example.testapp_v1.model.filialsEntity.Data
import com.example.testapp_v1.model.filialsEntity.DataDao
import com.example.testapp_v1.model.inventories.Inventory
import com.example.testapp_v1.model.inventories.InventoryDao
import com.example.testapp_v1.model.tdsDataEntity.TdsData

@Database(
    entities = [
        BranchData::class,
        Details::class,
        Data::class,
        Inventory::class,
        TdsData::class
    ],
    version = 3
)
abstract class DB : RoomDatabase() {

    abstract fun getBranchDataDao(): BranchDataDao
    abstract fun detailsDao(): DetailsDao
    abstract fun dataDao(): DataDao
    abstract fun getInventoryDao(): InventoryDao
    abstract fun getTdsDataDao(): TdsDataDao

    companion object {

        @Volatile
        private var instance: DB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            DB::class.java,
            "main_db"
        ).fallbackToDestructiveMigration().build()

    }

}