package com.example.foodtrck.data.local

import androidx.room.TypeConverter
import com.example.foodtrck.data.model.FoodTruck
import com.google.gson.Gson

class FoodTruckConverter {
    @TypeConverter
    fun fromImagesString(value: String?): FoodTruck.Images? {
        val gson = Gson()
        return gson.fromJson(value, FoodTruck.Images::class.java)
    }

    @TypeConverter
    fun imagesToString(images: FoodTruck.Images?): String? {
        val gson = Gson()
        return gson.toJson(images) ?: ""
    }
}