package com.example.foodtrck.repository

import androidx.lifecycle.MutableLiveData
import com.example.foodtrck.*
import com.example.foodtrck.data.local.FoodTruckDao
import com.example.foodtrck.data.local.RegionDao
import com.example.foodtrck.data.model.FoodTruckResponse
import com.example.foodtrck.data.model.GooglePlaceResponse
import com.example.foodtrck.data.model.Region
import com.example.foodtrck.data.remote.*
import com.example.foodtrck.data.repository.StreetFoodRepository
import com.example.foodtrck.utils.Resource
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.robolectric.RobolectricTestRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class StreetFoodRepositoryTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var regionDao: RegionDao
    private lateinit var foodTruckDao: FoodTruckDao
    private lateinit var streetFoodService: StreetFoodService
    private lateinit var streetFoodRemoteDataSource: StreetFoodRemoteDataSource
    private lateinit var googlePlaceRemoteDataSource: GooglePlaceRemoteDataSource
    private lateinit var googlePlaceService: GooglePlaceService
    private lateinit var repository: StreetFoodRepository

    @Before
    fun init() {
        initStreetFoodService()
        initGooglePlaceService()
        initDao()
        initRepository()
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
    }

    private fun initStreetFoodService() {
        streetFoodService = mock {
            onBlocking { getRegions() } doReturn Response.success(mockRegionList)
        }

        streetFoodService = mock {
            onBlocking { getFoodTrucks(anyString()) } doReturn
                    Response.success(FoodTruckResponse(mapOf("bon-me" to mockFoodTruck)))
        }

        streetFoodRemoteDataSource = StreetFoodRemoteDataSource(streetFoodService)
    }

    private fun initGooglePlaceService() {
        googlePlaceService = mock {
            onBlocking { searchPlace(anyString(), anyString()) } doReturn
                    Response.success(GooglePlaceResponse(mockPlacePhotoList))
        }

        googlePlaceRemoteDataSource = GooglePlaceRemoteDataSource(googlePlaceService)
    }

    private fun initDao() {
        regionDao = mock {
            on { getAll() } doReturn mockRegionList
        }

        foodTruckDao = mock {
            on { getAll() } doReturn mockFoodTruckList
            on { get(anyString()) } doReturn MutableLiveData(mockFoodTruck)
        }
    }

    private fun initRepository() {
        repository = StreetFoodRepository(
            googlePlaceRemoteDataSource,
            streetFoodRemoteDataSource,
            regionDao,
            foodTruckDao,
            testDispatcher
        )
    }

    @Test
    fun `test fetch regions`() = runBlocking {
        val flow = repository.fetchRegions()
        val result: List<Resource<List<Region>>?> = flow.toList()

        assertThat(result.first(), `is`(Resource.success(mockRegionList)))
    }

    @Test
    fun `test fetch foodtrucks`() = runBlocking {
        val flow = repository.fetchFoodTrucks("boston")
        val result: List<Resource<FoodTruckResponse>?> = flow.toList()

        assertThat(result.last(),
            `is`(Resource.success(FoodTruckResponse(mapOf("bon-me" to mockFoodTruck)))))
    }


    @Test
    fun `test fetch foodtruck`() = runBlocking {
        val foodtruckLiveData = repository.fetchFoodTruck("bon-me")
        val result = foodtruckLiveData
        val expected = mockFoodTruck

        assertThat(result.getOrAwaitValue(), `is`(expected))
    }
}