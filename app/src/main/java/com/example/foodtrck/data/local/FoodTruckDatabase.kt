package com.example.foodtrck.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.foodtrck.data.model.FoodTruck

@Database(entities = [FoodTruck::class], version = 1, exportSchema = false)
abstract class FoodTruckDatabase: RoomDatabase() {

    abstract fun foodTruckDao(): FoodTruckDao
}