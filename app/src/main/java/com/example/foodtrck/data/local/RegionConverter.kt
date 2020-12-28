package com.example.foodtrck.data.local

import androidx.room.TypeConverter
import com.example.foodtrck.data.model.GooglePlacePhotoItem
import com.example.foodtrck.utils.convertJsonToObj
import com.example.foodtrck.utils.convertToJson

class RegionConverter {
    @TypeConverter
    fun fromGooglePlacePhotoItemJson(value: String?): GooglePlacePhotoItem {
        return convertJsonToObj(value)
    }

    @TypeConverter
    fun googlePlacePhotoItemToJson(googlePlacePhotoItem: GooglePlacePhotoItem?): String? {
        return convertToJson(googlePlacePhotoItem)
    }
}
