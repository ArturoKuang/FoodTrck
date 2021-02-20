package com.example.foodtrck.network

import MainCoroutinesRule
import com.example.foodtrck.data.model.FoodTruck
import com.example.foodtrck.data.model.Region
import com.example.foodtrck.data.model.ScheduleInfo
import com.example.foodtrck.data.remote.StreetFoodService
import com.example.foodtrck.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import org.junit.Assert.*

class FoodTruckServiceTest : ApiAbstract<StreetFoodService>() {

    private lateinit var service: StreetFoodService

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineRule = MainCoroutinesRule()

    @Before
    fun initService() {
        service = createService(StreetFoodService::class.java)
    }

    @Throws(IOException::class)
    @Test
    fun getRegions() = runBlocking {
        enqueueResponse("./Regions.json")
        val response = service.getRegions()
        val responseBody = Resource(Resource.Status.SUCCESS, response.body(), "")
        mockWebServer.takeRequest()

        val expected = listOf(
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

        assertThat(responseBody.data, `is`(expected))
    }

    @Throws(IOException::class)
    @Test
    fun getFoodTrucks() = runBlocking {
        enqueueResponse("./BostonFoodTrucks.json")
        val response = service.getFoodTrucks("boston")
        val responseBody = Resource(Resource.Status.SUCCESS, response.body(), "")
        mockWebServer.takeRequest()

        val expectedSchedules = listOf(
            ScheduleInfo(start=1614009600, end=1614025800, display="Dewey Square, Boston", latitude=42.35286711, longitude=-71.05556734),
            ScheduleInfo(start=1614096000, end=1614112200, display="Dewey Square, Boston", latitude=42.35286711, longitude=-71.05556734),
            ScheduleInfo(start=1614182400, end=1614198600, display="Dewey Square, Boston", latitude=42.35286711, longitude=-71.05556734),
            ScheduleInfo(start=1614268800, end=1614285000, display="Dewey Square, Boston", latitude=42.35286711, longitude=-71.05556734),
            ScheduleInfo(start=1614355200, end=1614371400, display="Dewey Square, Boston", latitude=42.35286711, longitude=-71.05556734)
        )

        val expected = FoodTruck(
            id = "bon-me",
            rating = 890,
            name = "Bon Me 1",
            url = "bonmetruck.com",
            phone = "(617) 989-9804",
            email = "info@bonmetruck.com",
            description = "Bold, Fresh, and Fun Asian cuisine.",
            description_short = null,
            images = null,
            expectedSchedules
        )

        assertEquals(expected, responseBody.data?.vendors?.get("bon-me"))
    }
}