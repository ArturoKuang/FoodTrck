package com.example.foodtrck.data.repository

import com.example.foodtrck.data.local.RegionDao
import com.example.foodtrck.data.model.Region
import com.example.foodtrck.data.remote.StreetFoodRemoteDataSource
import com.example.foodtrck.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class StreetFoodRepository @Inject constructor(
    private val remoteDataSource: StreetFoodRemoteDataSource,
    private val regionDao: RegionDao
) {

    suspend fun fetchRegions() : Flow<Resource<List<Region>>?> {
        return flow {
            emit(fetchRegionsCache())
            emit(Resource.loading())

            val result = remoteDataSource.getRegions()

            if(result.status == Resource.Status.SUCCESS) {
                result.data?.let { it ->
                    regionDao.deleteAll(it)
                    regionDao.insertAll(it)
                }
            }

            emit(result)

        }.flowOn(Dispatchers.IO)
    }


    private fun fetchRegionsCache() : Resource<List<Region>>? =
        regionDao.getAll()?.let {
            Resource.success(it)
    }

}