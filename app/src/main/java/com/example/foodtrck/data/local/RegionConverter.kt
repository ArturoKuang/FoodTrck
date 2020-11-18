package com.example.foodtrck.data.local

import androidx.room.TypeConverter
import com.example.foodtrck.data.model.Region
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RegionConverter {
    val gson = Gson()

    @TypeConverter
    fun fromLocationString(value: String?): Region.Location? {
        return gson.fromJson(value, Region.Location::class.java)
    }

    @TypeConverter
    fun toLocationToString(location: Region.Location): String? {
        return gson.toJson(location) ?: ""
    }

    @TypeConverter
    fun fromLocationListString(value: String?): List<Region.Location>? {
        if(value.isNullOrBlank()) {
            return emptyList()
        }

        return gson.fromJson(value, object : TypeToken<List<Region.Location>>() {}.type)
    }

    @TypeConverter
    fun toLocationListString(locations: List<Region.Location>): String? {
        return gson.toJson(locations) ?: ""
    }
}