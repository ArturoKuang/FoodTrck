package com.example.foodtrck.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

fun <T, A> performGetOperation(
    networkCall: suspend () -> Resource<A>,
    dataBaseQuery: suspend () -> Resource<T>,
    saveCallResult: suspend (A) -> Unit): Flow<Result<T>?> {

    return flow {

    }
}