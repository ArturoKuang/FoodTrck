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
    @SerializedName("regions")
    val locations: List<Location>? = null
) {
    data class Location(val identifier: String, val name: String)
}