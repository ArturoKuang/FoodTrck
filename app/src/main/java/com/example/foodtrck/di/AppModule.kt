package com.example.foodtrck.di

import android.content.Context
import androidx.room.Room
import com.example.foodtrck.data.local.RegionDao
import com.example.foodtrck.data.local.RegionDatabase
import com.example.foodtrck.data.remote.StreetFoodRemoteDataSource
import com.example.foodtrck.data.remote.StreetFoodService
import com.example.foodtrck.data.repository.StreetFoodRepository
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
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    private const val BASE_URL = "http://data.streetfoodapp.com/1.1/"

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }


    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideCharacterService(retrofit: Retrofit): StreetFoodService = retrofit.create(StreetFoodService::class.java)

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
    fun provideRegionDao(regionDatabase: RegionDatabase): RegionDao {
        return regionDatabase.regionDao()
    }

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: StreetFoodRemoteDataSource, regionDao: RegionDao) =
        StreetFoodRepository(remoteDataSource, regionDao)
}