package com.example.foodtrck.data.repository

import com.example.foodtrck.data.local.FoodTruckDao
import com.example.foodtrck.data.local.RegionDao
import com.example.foodtrck.data.model.FoodTruck
import com.example.foodtrck.data.model.Region
import com.example.foodtrck.data.remote.StreetFoodRemoteDataSource
import com.example.foodtrck.utils.Resource
import com.example.foodtrck.utils.performGetFlowOperation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StreetFoodRepository @Inject constructor(
    private val remoteDataSource: StreetFoodRemoteDataSource,
    private val regionDao: RegionDao,
    private val foodTruckDao: FoodTruckDao
) {
    suspend fun fetchRegions(): Flow<Resource<List<Region>>?> {
        return performGetFlowOperation (
            networkCall = { remoteDataSource.getRegions() },
            dataBaseQuery = { fetchRegionsCache() },
            saveCallResult = { saveToRegionsDatabase(it) }
        )
    }

    suspend fun fetchFoodTrucks(regions: String): Flow<Resource<List<FoodTruck>>?> {
        return performGetFlowOperation(
            networkCall = { remoteDataSource.getFoodTrucks(regions) },
            dataBaseQuery = { fetchFoodTruckCache() },
            saveCallResult = { saveToFoodTruckDatabase(it) }
        )
    }

    private fun saveToFoodTruckDatabase(data: List<FoodTruck>) {
        foodTruckDao.deleteAll(data)
        foodTruckDao.insertAll(data)
    }

    private fun fetchFoodTruckCache(): Resource<List<FoodTruck>>? =
        foodTruckDao.getAll()?.let {
            Resource.success(it)
        }


    private fun saveToRegionsDatabase(data: List<Region>) {
        regionDao.deleteAll(data)
        regionDao.insertAll(data)
    }

    private fun fetchRegionsCache() : Resource<List<Region>>? =
        regionDao.getAll()?.let {
            Resource.success(it)
    }

}