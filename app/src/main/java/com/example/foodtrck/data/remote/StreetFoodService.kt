package com.example.foodtrck.data.remote

import com.example.foodtrck.data.model.Region
import com.example.foodtrck.data.model.RegionResponse
import retrofit2.Response
import retrofit2.http.GET

interface StreetFoodService {
    @GET("regions")
    suspend fun getRegions() : Response<RegionResponse>

//    @GET("schedule/{city}")
//    suspend fun getSchedules(@Path("city") city: String)
}