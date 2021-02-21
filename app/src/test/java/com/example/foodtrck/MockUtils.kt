package com.example.foodtrck

import com.example.foodtrck.data.model.FoodTruck
import com.example.foodtrck.data.model.Region
import com.example.foodtrck.data.model.ScheduleInfo

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
    ScheduleInfo(start=1614009600, end=1614025800, display="Dewey Square, Boston", latitude=42.35286711, longitude=-71.05556734),
    ScheduleInfo(start=1614096000, end=1614112200, display="Dewey Square, Boston", latitude=42.35286711, longitude=-71.05556734),
    ScheduleInfo(start=1614182400, end=1614198600, display="Dewey Square, Boston", latitude=42.35286711, longitude=-71.05556734),
    ScheduleInfo(start=1614268800, end=1614285000, display="Dewey Square, Boston", latitude=42.35286711, longitude=-71.05556734),
    ScheduleInfo(start=1614355200, end=1614371400, display="Dewey Square, Boston", latitude=42.35286711, longitude=-71.05556734)
)

val mockFoodTruck = FoodTruck(
    id = "bon-me",
    rating = 890,
    name = "Bon Me 1",
    url = "bonmetruck.com",
    phone = "(617) 989-9804",
    email = "info@bonmetruck.com",
    description = "Bold, Fresh, and Fun Asian cuisine.",
    description_short = null,
    images = null,
    mockSchedules
)