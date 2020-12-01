package com.example.foodtrck.data.local

import androidx.room.TypeConverter
import com.example.foodtrck.data.model.FoodTruck
import com.example.foodtrck.utils.convertJsonToList
import com.example.foodtrck.utils.convertJsonToObj
import com.example.foodtrck.utils.convertToJson
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
    fun fromScheduleInfoListString(value: String?): List<FoodTruck.ScheduleInfo>? {
        return convertJsonToList<FoodTruck.ScheduleInfo>(value)
    }

    @TypeConverter
    fun scheduleInfoListToString(scheduleInfo: List<FoodTruck.ScheduleInfo>): String? {
        return convertToJson(scheduleInfo)
    }
}