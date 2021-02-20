package com.example.foodtrck.network

import MainCoroutinesRule
import com.example.foodtrck.data.model.GooglePlacePhotoItem
import com.example.foodtrck.data.remote.GooglePlaceService
import com.example.foodtrck.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class GooglePlaceServiceTest : ApiAbstract<GooglePlaceService>() {

    private lateinit var service: GooglePlaceService

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineRule = MainCoroutinesRule()

    @Before
    fun initService() {
        service = createService(GooglePlaceService::class.java)
    }

    @Throws(IOException::class)
    @Test
    fun fetchPlacePhotos() = runBlocking {
        enqueueResponse("/GooglePlace.json")
        val response = service.searchPlace("boston,ma", "42.3600825,-71.0588801")
        val responseBody = Resource(Resource.Status.SUCCESS, response.body(), "")
        mockWebServer.takeRequest()

        val expected =
            GooglePlacePhotoItem("ATtYBwJMG2p5cpfs7Exwo4Ec4TER2Cb7DJTd21XmGWXSOABBm1fy8whDbfplVT00gxHmDUHVbP8h4NidKjrD_1bGWHshtkxIrM0m0QyIhtaDICO4GyS3IhF3Ys01m03HSnpNtR9l8M6grJ6riZkBjcUnyQmCf-pKqIeP1qvrW3dwgsZQkb7B")
        assertEquals(expected, responseBody.data?.results?.first()?.photos?.first())
    }
}