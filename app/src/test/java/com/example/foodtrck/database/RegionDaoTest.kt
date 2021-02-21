package com.example.foodtrck.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.example.foodtrck.data.local.RegionDao
import com.example.foodtrck.data.local.RegionDatabase
import com.example.foodtrck.data.model.Region
import com.example.foodtrck.mockRegionList
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class RegionDaoTest {
    lateinit var db: RegionDatabase
    lateinit var regionDao: RegionDao

    @Before
    fun init() {
        db = Room.inMemoryDatabaseBuilder(getApplicationContext(), RegionDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        regionDao = db.regionDao()
    }

    @After
    fun closeDB() {
        db.close()
    }

    @Test
    fun insertAllRegionTest() {
        val expected = mockRegionList
        regionDao.insertAll(mockRegionList)
        val loadFromDB = regionDao.getAll()

        assertTrue(expected.containsAll(loadFromDB!!))
    }

    @Test
    fun deleteRegionTest() {
        val expected = mockRegionList.subList(1, mockRegionList.size)
        regionDao.insertAll(mockRegionList)
        regionDao.delete(mockRegionList.first())
        val loadFromDB = regionDao.getAll()

        assertTrue(expected.containsAll(loadFromDB!!))
    }

    @Test
    fun deleteAllRegionTest() {
        val expected = emptyList<Region>()
        regionDao.insertAll(mockRegionList)
        regionDao.deleteAll(mockRegionList)
        val loadFromDB = regionDao.getAll()

        assertTrue(expected.containsAll(loadFromDB!!))
    }
}