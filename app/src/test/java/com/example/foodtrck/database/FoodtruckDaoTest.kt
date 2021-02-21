package com.example.foodtrck.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.example.foodtrck.data.local.FoodTruckDao
import com.example.foodtrck.data.local.FoodTruckDatabase
import com.example.foodtrck.data.model.FoodTruck
import com.example.foodtrck.getOrAwaitValue
import com.example.foodtrck.mockFoodTruck
import com.example.foodtrck.mockFoodTruckList
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FoodtruckDaoTest {
    @Rule
    @JvmField
    val instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var db: FoodTruckDatabase
    lateinit var foodtruckDao: FoodTruckDao

    @Before
    fun init() {
        db = Room.inMemoryDatabaseBuilder(getApplicationContext(), FoodTruckDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        foodtruckDao = db.foodTruckDao()
    }

    @After
    fun closeDB() {
        db.close()
    }

    @Test
    fun insertAllFoodtrucksTest() {
        val expected = mockFoodTruckList
        foodtruckDao.insertAll(mockFoodTruckList)
        val loadFromDB = foodtruckDao.getAll()

        assertTrue(expected.containsAll(loadFromDB!!))
    }

    @Test
    fun getFoodTruckTest() {
        val expected = mockFoodTruck
        foodtruckDao.insertAll(mockFoodTruckList)
        val loadFromDB = foodtruckDao.get("bon-me")

        assertEquals(expected, loadFromDB.getOrAwaitValue())
    }

    @Test
    fun deleteFoodtruckTest() {
        val expected = mockFoodTruckList.subList(1, mockFoodTruckList.size)
        foodtruckDao.insertAll(mockFoodTruckList)
        foodtruckDao.delete(mockFoodTruck)
        val loadFromDB = foodtruckDao.getAll()

        assertTrue(expected.containsAll(loadFromDB!!))
    }

    @Test
    fun deleteAllFoodtruckTest() {
        val expected = emptyList<FoodTruck>()
        foodtruckDao.insertAll(mockFoodTruckList)
        foodtruckDao.deleteAll(mockFoodTruckList)
        val loadFromDB = foodtruckDao.getAll()

        assertTrue(expected.containsAll(loadFromDB!!))
    }
}