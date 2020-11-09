package com.example.foodtrck.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface StreetFoodAppService {
    @GET("regions")
    suspend fun getRegions()

    @GET("schedule/{city}")
    suspend fun getSchedule(@Path("city") city: String)

    companion object {
        private const val BASE_URL = "http://data.streetfoodapp.com/1.1/"

        fun create(): StreetFoodAppService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(StreetFoodAppService::class.java)
        }
    }
}