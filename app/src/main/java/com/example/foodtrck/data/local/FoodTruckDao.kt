package com.example.foodtrck.data.local

import androidx.room.*
import com.example.foodtrck.data.model.FoodTruck

@Dao
interface FoodTruckDao {
    @Query("SELECT * FROM foodtrucks order by id DESC")
    fun getAll(): List<FoodTruck>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(region: List<FoodTruck>)

    @Delete
    fun delete(foodTruck: FoodTruck)

    @Delete
    fun deleteAll(foodTruck: List<FoodTruck>)
}