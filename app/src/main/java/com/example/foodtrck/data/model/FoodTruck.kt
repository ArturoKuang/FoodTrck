package com.example.foodtrck.data.model

import android.location.Location
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodtrck.utils.convertToRoundedMiles
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
    val email: String?,
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

    //returns Miles, and -1 if food truck is not open
    fun distanceAwayFrom(location: Location): Float {
        val foodtruckLocation: Location = getCurrentSchedule()?.getLocation() ?: return -1f
        var distance = location.distanceTo(foodtruckLocation)
        distance = convertToRoundedMiles(distance)
        return distance
    }

    fun getCurrentSchedule(): ScheduleInfo? {
        val today = Calendar.getInstance()
        var currentSchedule: ScheduleInfo? = null

        for (scheduleInfo in schedule) {
            val calenderDay = convertDateToCalender(scheduleInfo.getStartDate())
            if(dayAndMonthEqual(today, calenderDay)) {
               currentSchedule = scheduleInfo
            }
        }

        return currentSchedule
    }

    private fun dayAndMonthEqual(a: Calendar, b: Calendar): Boolean {
        return a.get(Calendar.DATE) == b.get(Calendar.DATE) &&
                a.get(Calendar.MONTH) == b.get(Calendar.MONTH)
    }

    private fun convertDateToCalender(date: Date): Calendar {
        val cal = Calendar.getInstance()
        cal.time = date
        return cal
    }
}

