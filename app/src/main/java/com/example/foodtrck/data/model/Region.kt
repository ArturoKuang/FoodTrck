package com.example.foodtrck.data.model

import com.google.gson.annotations.SerializedName

data class Region(
    val name: String,
    @SerializedName("name_long")
    val nameLong: String
)