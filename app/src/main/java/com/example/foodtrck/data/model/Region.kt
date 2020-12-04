package com.example.foodtrck.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
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
    val image: GooglePlacePhotoItem? = null
) {
}