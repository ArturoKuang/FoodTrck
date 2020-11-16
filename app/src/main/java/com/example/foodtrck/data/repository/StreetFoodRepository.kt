package com.example.foodtrck.data.repository

import com.example.foodtrck.data.local.RegionDao
import com.example.foodtrck.data.model.Region
import com.example.foodtrck.data.remote.StreetFoodRemoteDataSource
import com.example.foodtrck.utils.Resource
import com.example.foodtrck.utils.performGetFlowOperation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StreetFoodRepository @Inject constructor(
    private val remoteDataSource: StreetFoodRemoteDataSource,
    private val regionDao: RegionDao
) {
    suspend fun fetchRegions(): Flow<Resource<List<Region>>?> {
        return performGetFlowOperation (
            networkCall = { remoteDataSource.getRegions() },
            dataBaseQuery = { fetchRegionsCache() },
            saveCallResult = { saveToRegionsDatabase(it) }
        )
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