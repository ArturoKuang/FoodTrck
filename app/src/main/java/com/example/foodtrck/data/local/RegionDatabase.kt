package com.example.foodtrck.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.foodtrck.data.model.Region

@Database(entities = [Region::class], version = 1, exportSchema = false)
abstract class RegionDatabase : RoomDatabase() {

    abstract fun regionDao() : RegionDao
}