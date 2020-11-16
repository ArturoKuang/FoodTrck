package com.example.foodtrck.utils

import com.example.foodtrck.data.model.Region
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

fun <A> performGetFlowOperation(
    networkCall: suspend () -> Resource<A>,
    dataBaseQuery: () -> Resource<A>?,
    saveCallResult: suspend (A) -> Unit): Flow<Resource<A>?> {

    return flow {
        emit(dataBaseQuery())

        emit(Resource.loading())

        val result = networkCall.invoke()

        if(result.status == Resource.Status.SUCCESS) {
            result.data?.let { data ->
                saveCallResult(data)
            }
        }

        emit(result)

    }.flowOn(Dispatchers.IO)
}