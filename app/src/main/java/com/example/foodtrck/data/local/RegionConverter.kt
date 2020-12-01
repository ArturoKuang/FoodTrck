package com.example.foodtrck.data.local

import androidx.room.TypeConverter
import com.example.foodtrck.data.model.Region
import com.example.foodtrck.utils.convertJsonToList
import com.example.foodtrck.utils.convertJsonToObj
import com.example.foodtrck.utils.convertToJson
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RegionConverter {
    val gson = Gson()

    @TypeConverter
    fun fromLocationString(value: String?): Region.Location? {
        return convertJsonToObj(value)
    }

    @TypeConverter
    fun toLocationToString(location: Region.Location): String? {
        return convertToJson(location)
    }

    @TypeConverter
    fun fromLocationListString(value: String?): List<Region.Location>? {
        return convertJsonToList<Region.Location>(value)
    }

    @TypeConverter
    fun toLocationListString(locations: List<Region.Location>): String? {
        return convertToJson(locations)
    }
}