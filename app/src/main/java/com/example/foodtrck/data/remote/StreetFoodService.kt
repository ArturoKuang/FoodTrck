package com.example.foodtrck.data.remote

import com.example.foodtrck.data.model.Region
import retrofit2.Response
import retrofit2.http.GET

interface StreetFoodService {
    @GET("regions")
    suspend fun getRegions() : Response<List<Region>>

//    @GET("schedule/{city}")
//    suspend fun getSchedules(@Path("city") city: String)
}