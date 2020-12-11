package com.example.foodtrck.data.local

import androidx.room.TypeConverter
import com.example.foodtrck.data.model.FoodTruck
import com.example.foodtrck.data.model.ScheduleInfo
import com.example.foodtrck.utils.convertJsonToList
import com.example.foodtrck.utils.convertJsonToObj
import com.example.foodtrck.utils.convertToJson
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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
    fun fromScheduleInfoListString(value: String?): List<ScheduleInfo> {
        val gson = Gson()
        if(value.isNullOrBlank()) {
            return emptyList()
        }

       val test: List<ScheduleInfo> = gson.fromJson(value, object : TypeToken<List<ScheduleInfo>>() {}.type)
        return test
    }

    @TypeConverter
    fun scheduleInfoListToString(scheduleInfo: List<ScheduleInfo>): String? {
        return convertToJson(scheduleInfo)
    }
}