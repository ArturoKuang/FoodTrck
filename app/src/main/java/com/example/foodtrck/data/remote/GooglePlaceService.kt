package com.example.foodtrck.data.remote

import com.example.foodtrck.data.model.GooglePlaceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GooglePlaceService {
    @GET("textsearch/json?")
    suspend fun searchPlace(
        @Query("query") query: String,
        @Query("location") location: String) : Response<GooglePlaceResponse>
}