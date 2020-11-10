package com.example.foodtrck.data.remote

import javax.inject.Inject

class StreetFoodRemoteDataSource @Inject constructor(
        private val streetFoodService: StreetFoodService
) : BaseDataSource() {

    suspend fun getRegions() = getResult { streetFoodService.getRegions() }

    //TODO
    //suspend fun getFoodTrucks
}