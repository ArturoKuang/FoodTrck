package com.example.foodtrck.data.repository

import android.graphics.Region
import androidx.lifecycle.liveData
import com.example.foodtrck.data.remote.StreetFoodRemoteDataSource
import com.example.foodtrck.utils.Resource
import com.example.foodtrck.utils.performGetOperation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import okhttp3.Dispatcher
import timber.log.Timber
import javax.inject.Inject

class StreetFoodRepository @Inject constructor(
    private val remoteDataSource: StreetFoodRemoteDataSource
) {

    suspend fun getRegions() = liveData<Resource<List<Region>>>(Dispatchers.IO) {
        emit(Resource.loading())

        val responseStatus = remoteDataSource.getRegions()
        if (responseStatus.status == Resource.Status.SUCCESS) {
            Timber.d("Response Success ${responseStatus.data}")

        } else if (responseStatus.status == Resource.Status.ERROR) {
            emit(Resource.error(responseStatus.message!!))
        }
    }

}