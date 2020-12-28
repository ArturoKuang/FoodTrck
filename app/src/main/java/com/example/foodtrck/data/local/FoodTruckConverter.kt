package com.example.foodtrck.data.local

import androidx.room.TypeConverter
import com.example.foodtrck.data.model.FoodTruck
import com.example.foodtrck.data.model.ScheduleInfo
import com.example.foodtrck.utils.convertJsonToObj
import com.example.foodtrck.utils.convertToJson
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FoodTruckConverter {
    @TypeConverter
    fun fromImagesJson(value: String?): FoodTruck.Images? {
        return convertJsonToObj(value)
    }

    @TypeConverter
    fun imagesToJson(images: FoodTruck.Images?): String? {
        return convertToJson(images)
    }

    @TypeConverter
    fun fromScheduleInfoListJson(value: String?): List<ScheduleInfo> {
        val gson = Gson()
        if (value.isNullOrBlank()) {
            return emptyList()
        }

        return gson.fromJson(
            value,
            object : TypeToken<List<ScheduleInfo>>() {}.type
        )
    }

    @TypeConverter
    fun scheduleInfoListToJson(scheduleInfo: List<ScheduleInfo>): String? {
        return convertToJson(scheduleInfo)
    }
}
