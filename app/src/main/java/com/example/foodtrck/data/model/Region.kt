package com.example.foodtrck.data.model

import android.location.Location
import android.location.LocationManager
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodtrck.utils.createLocation
import com.google.gson.annotations.SerializedName

@Entity(tableName = "regions")
data class Region(
    @PrimaryKey
    @SerializedName("identifier")
    val id: String,
    val name: String,
    @SerializedName("name_long")
    val nameLong: String?,
    val latitude: Double,
    val longitude: Double,
    var image: GooglePlacePhotoItem? = null
) {
    fun getLocation(): Location {
        return createLocation(latitude, longitude)
    }
}