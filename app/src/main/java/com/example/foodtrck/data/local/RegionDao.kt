package com.example.foodtrck.data.local

import androidx.room.*
import com.example.foodtrck.data.model.Region

@Dao
interface RegionDao {
    @Query("SELECT * FROM regions order by nameLong DESC")
    fun getAll(): List<Region>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(region: List<Region>)

    @Delete
    fun delete(region: Region)

    @Delete
    fun deleteAll(region: List<Region>)
}
