package com.example.foodtrck.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodtrck.data.model.FoodTruck

@Dao
interface FoodTruckDao {
    @Query("SELECT * FROM foodtrucks order by id DESC")
    fun getAll(): List<FoodTruck>?

    @Query("SELECT * FROM foodtrucks WHERE id=(:foodTruckID)")
    fun get(foodTruckID: String): LiveData<FoodTruck?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(region: List<FoodTruck>)

    @Delete
    fun delete(foodTruck: FoodTruck)

    @Delete
    fun deleteAll(foodTruck: List<FoodTruck>)
}
