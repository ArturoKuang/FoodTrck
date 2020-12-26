package com.example.foodtrck.data.model
import android.location.Location
import com.example.foodtrck.utils.convertTrimmedDate
import com.example.foodtrck.utils.createLocation
import java.util.*

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
        return now.before(getEndDate())
    }

    fun getLocation(): Location {
        return createLocation(latitude, longitude)
    }
}
