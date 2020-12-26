package com.example.foodtrck.data.remote

import com.example.foodtrck.data.model.FoodTruckResponse
import com.example.foodtrck.data.model.Region
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface StreetFoodService {
    @GET("regions")
    suspend fun getRegions(): Response<List<Region>>

    @GET("schedule/{region}")
    suspend fun getFoodTrucks(@Path("region") region: String): Response<FoodTruckResponse>
}
