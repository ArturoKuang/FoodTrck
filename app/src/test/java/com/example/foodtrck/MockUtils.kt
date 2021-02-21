package com.example.foodtrck

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.foodtrck.data.model.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

val mockRegionList = listOf(
    Region(
        id = "abbotsford",
        name = "Abbotsford",
        nameLong = "Abbotsford, BC",
        latitude = 49.0504377,
        longitude = -122.3044697
    ),
    Region(
        id = "amsterdam-ny",
        name = "Amsterdam",
        nameLong = "Amsterdam, NY",
        latitude = 42.937546063804,
        longitude = -74.190823048836
    ),
    Region(
        id = "ballarat",
        name = "Ballarat",
        nameLong = "Ballarat, Australia",
        latitude = -37.497362927445,
        longitude = 143.83544921875
    )
)

val mockSchedules = listOf(
    ScheduleInfo(
        start = 1614009600,
        end = 1614025800,
        display = "Dewey Square, Boston",
        latitude = 42.35286711,
        longitude = -71.05556734
    ),
    ScheduleInfo(
        start = 1614096000,
        end = 1614112200,
        display = "Dewey Square, Boston",
        latitude = 42.35286711,
        longitude = -71.05556734
    ),
    ScheduleInfo(
        start = 1614182400,
        end = 1614198600,
        display = "Dewey Square, Boston",
        latitude = 42.35286711,
        longitude = -71.05556734
    ),
    ScheduleInfo(
        start = 1614268800,
        end = 1614285000,
        display = "Dewey Square, Boston",
        latitude = 42.35286711,
        longitude = -71.05556734
    ),
    ScheduleInfo(
        start = 1614355200,
        end = 1614371400,
        display = "Dewey Square, Boston",
        latitude = 42.35286711,
        longitude = -71.05556734
    )
)

val mockFoodTruck = FoodTruck(
    id = "bon-me",
    rating = 890,
    name = "Bon Me 1",
    url = "bonmetruck.com",
    phone = "(999) 999-9999",
    email = "info@bonmetruck.com",
    description = "Bold, Fresh, and Fun Asian cuisine.",
    description_short = null,
    images = null,
    mockSchedules
)


val mockFoodTruckList = listOf(
    FoodTruck(
        id = "bon-me",
        rating = 890,
        name = "Bon Me 1",
        url = "bonmetruck.com",
        phone = "(999) 999-9999",
        email = "info@bonmetruck.com",
        description = "Bold, Fresh, and Fun Asian cuisine.",
        description_short = null,
        images = null,
        mockSchedules
    ),
    FoodTruck(
        id = "bon-me-2",
        rating = 990,
        name = "Bon Me 2",
        url = "bonmetruck.com",
        phone = "(000) 000-0000",
        email = "info@bonmetruck.com",
        description = "Bold, Fresh, and Fun Asian cuisine.",
        description_short = null,
        images = null,
        mockSchedules
    ),
    FoodTruck(
        id = "bon-me-3",
        rating = 1090,
        name = "Bon Me 3",
        url = "bonmetruck.com",
        phone = "(111) 111-1111",
        email = "info@bonmetruck.com",
        description = "Bold, Fresh, and Fun Asian cuisine.",
        description_short = null,
        images = null,
        mockSchedules
    )
)

val mockPlacePhotoList = listOf(
    GooglePlacePhotos(listOf(GooglePlacePhotoItem((""))))
)

fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }

    this.observeForever(observer)

    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}