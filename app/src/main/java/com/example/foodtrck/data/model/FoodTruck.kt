package com.example.foodtrck.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodtrck.utils.convertTrimmedDate
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "FoodTrucks")
data class FoodTruck(
    @SerializedName("identifier")
    @PrimaryKey
    val id: String,
    val rating: Int,
    val name: String,
    val url: String?,
    val phone: String?,
    val description: String?,
    val description_short: String?,
    val images: Images? = null,
    @SerializedName("open")
    val schedule: List<ScheduleInfo>
) {
    data class Images(
        var logo: String,
        var logo_small: String,
        var header: List<String>
    )

    data class ScheduleInfo(
        val start: Long,
        val end: Long,
        val display: String,
        val latitude: Double,
        val longitude: Double
    ) {

        fun getStartDate(): Date {
            return convertTrimmedDate(start)
        }

        fun getEndDate(): Date {
            return convertTrimmedDate(end)
        }

        fun isOpen(): Boolean {
            val now = Date()
            return now > getEndDate()
        }
    }
}

