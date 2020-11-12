package com.example.foodtrck.data.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "regions")
data class Region(
    @NonNull
    @PrimaryKey
    @SerializedName("identifier")
    val id: String,
    val name: String,
    @SerializedName("name_long")
    val nameLong: String
)