package com.example.foodtrck.di

import android.content.Context
import androidx.room.Room
import com.example.foodtrck.Config
import com.example.foodtrck.data.local.FoodTruckDao
import com.example.foodtrck.data.local.FoodTruckDatabase
import com.example.foodtrck.data.local.RegionDao
import com.example.foodtrck.data.local.RegionDatabase
import com.example.foodtrck.data.remote.*
import com.example.foodtrck.data.repository.StreetFoodRepository
import com.example.foodtrck.utils.GooglePlaceRetrofit
import com.example.foodtrck.utils.StreetFoodRetrofit
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DateFormat
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    @StreetFoodRetrofit
    fun provideStreetFoodRetrofit(gson: Gson) : Retrofit {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        return Retrofit.Builder()
            .baseUrl(Config.STREETFOOD_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    @GooglePlaceRetrofit
    fun provideGooglePlaceRetrofit(
        gson: Gson, googlePlaceInterceptor: GooglePlaceInterceptor): Retrofit {

        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC

        val client = OkHttpClient.Builder()
            .addInterceptor(googlePlaceInterceptor)
            .addInterceptor(logger)
            .build()

        return Retrofit.Builder()
            .baseUrl(Config.GOOGLEPLACE_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    fun googlePlaceInterceptor(): GooglePlaceInterceptor {
        return GooglePlaceInterceptor()
    }

    @Provides
    fun provideGson(): Gson =
        GsonBuilder()
            .setDateFormat(DateFormat.LONG)
            .create()

    @Provides
    fun provideStreetFoodService(@StreetFoodRetrofit retrofit: Retrofit): StreetFoodService =
        retrofit.create(StreetFoodService::class.java)

    @Provides
    fun provideGooglePlaceService(@GooglePlaceRetrofit retrofit: Retrofit): GooglePlaceService =
        retrofit.create(GooglePlaceService::class.java)

    @Provides
    @Singleton
    fun provideRegionDatabase(@ApplicationContext appContext: Context) : RegionDatabase {
        return Room.databaseBuilder(
            appContext,
            RegionDatabase::class.java,
            "region.db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesFoodTruckDatabase(@ApplicationContext appContext: Context): FoodTruckDatabase {
        return Room.databaseBuilder(
            appContext,
            FoodTruckDatabase::class.java,
            "foodtruck.db"
        ).build()
    }

    @Provides
    fun provideRegionDao(regionDatabase: RegionDatabase): RegionDao {
        return regionDatabase.regionDao()
    }

    @Provides
    fun provideFoodTruckDao(foodTruckDatabase: FoodTruckDatabase): FoodTruckDao {
        return foodTruckDatabase.foodTruckDao()
    }

    @Singleton
    @Provides
    fun provideRepository(
        googlePlaceRemoteDataSource: GooglePlaceRemoteDataSource,
        streetFoodRemoteDataSource: StreetFoodRemoteDataSource,
        regionDao: RegionDao,
        foodTruckDao: FoodTruckDao) : StreetFoodRepository {

        return StreetFoodRepository(googlePlaceRemoteDataSource, streetFoodRemoteDataSource, regionDao, foodTruckDao)
    }

}