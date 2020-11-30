package com.example.foodtrck.data.local

import androidx.room.TypeConverter
import com.example.foodtrck.data.model.FoodTruck
import com.example.foodtrck.utils.convertJsonToObj
import com.example.foodtrck.utils.convertToJson
import com.google.gson.Gson
import java.util.*

class FoodTruckConverter {
    @TypeConverter
    fun fromImagesString(value: String?): FoodTruck.Images? {
        return convertJsonToObj(value)
    }

    @TypeConverter
    fun imagesToString(images: FoodTruck.Images?): String? {
        return convertToJson(images)
    }

    @TypeConverter
    fun fromTimeStamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimeStamp(date: Date?): Long? {
        return date?.time
    }
}