package com.example.foodtrck

import android.location.Location
import android.location.LocationManager
import com.example.foodtrck.data.model.ScheduleInfo
import com.example.foodtrck.data.model.FoodTruck
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.*

@RunWith(RobolectricTestRunner::class)
class FoodTruckTest {

    private val trimmedDate = Calendar.getInstance().timeInMillis/1000
    private val mockSchedules = listOf(
        ScheduleInfo(
            start = trimmedDate,
            end = trimmedDate,
            display = "Boston, MA",
            latitude = 42.35286711,
            longitude = -71.05556734),

        ScheduleInfo(
            1614096000,
            1614112200,
            "Boston, MA",
            42.35286711,
            -71.05556734),

        ScheduleInfo(
            1614009600,
            1614025800,
            "Boston, MA",
            42.35286711,
            -71.05556734)
        )

    private val mockLocation = Location(LocationManager.GPS_PROVIDER)

    private val foodtruck = FoodTruck(
        id = "0",
        rating = 0,
        name = "Good FoodTruck",
        url = "foodtruck.com",
        phone = "123-123-123",
        email = "foodtruck@truck.com",
        description = "this is a good foodtruck",
        description_short = "",
        images = null,
        schedule = mockSchedules
    )

    @Before
    fun initLocation() {
        mockLocation.latitude = 42.395371
        mockLocation.longitude = -71.063289
    }

    @Test
    fun distanceAwayFrom() {
        val expected = 3.7f
        val delta = 1.0f

        assertEquals(expected, foodtruck.distanceAwayFrom(mockLocation), delta)
    }

    @Test
    fun getCurrentSchedule() {
        val expected = mockSchedules.first()

        assertEquals(expected, foodtruck.getCurrentSchedule())
    }
}