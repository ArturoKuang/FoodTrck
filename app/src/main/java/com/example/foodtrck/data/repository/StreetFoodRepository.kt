package com.example.foodtrck.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.foodtrck.data.local.FoodTruckDao
import com.example.foodtrck.data.local.RegionDao
import com.example.foodtrck.data.model.FoodTruck
import com.example.foodtrck.data.model.FoodTruckResponse
import com.example.foodtrck.data.model.Region
import com.example.foodtrck.data.remote.GooglePlaceRemoteDataSource
import com.example.foodtrck.data.remote.StreetFoodRemoteDataSource
import com.example.foodtrck.utils.Resource
import com.example.foodtrck.utils.performGetFlowOperation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class StreetFoodRepository @Inject constructor(
    private val googlePlaceRemoteDataSource: GooglePlaceRemoteDataSource,
    private val streetFoodRemoteDataSource: StreetFoodRemoteDataSource,
    private val regionDao: RegionDao,
    private val foodTruckDao: FoodTruckDao
) {
    suspend fun fetchRegions(): Flow<Resource<List<Region>>?> {
        googlePlaceRemoteDataSource.searchPhotos("boston,ma", "42.3600825,-71.0588801")
        return performGetFlowOperation (
            networkCall = { streetFoodRemoteDataSource.getRegions() },
            dataBaseQuery = { fetchRegionsCache() },
            saveCallResult = { saveToRegionsDatabase(it) }
        )
    }

    suspend fun fetchFoodTrucks(region: String): Flow<Resource<FoodTruckResponse>?> {
        return flow {
            emit(fetchFoodTruckCache())
            emit(Resource.loading())
        }.transform {
            val result = streetFoodRemoteDataSource.getFoodTrucks(region)

            if(result.status == Resource.Status.SUCCESS) {
                result.data?.vendors?.values.let { data ->
                    val list = data?.toList()
                    if (list != null) {
                        saveToFoodTruckDatabase(list)
                    }
                }
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun fetchFoodTruck(foodTruckID: String): LiveData<FoodTruck?> =
        liveData(Dispatchers.IO) {
            emitSource(foodTruckDao.get(foodTruckID))
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