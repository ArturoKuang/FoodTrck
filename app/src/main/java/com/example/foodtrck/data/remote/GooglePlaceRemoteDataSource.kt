package com.example.foodtrck.data.remote

import javax.inject.Inject

class GooglePlaceRemoteDataSource @Inject constructor(
    private val googlePlaceService: GooglePlaceService
) : BaseDataSource() {

    suspend fun searchPhotos(query: String, location: String) =
        getResult { googlePlaceService.searchPlace(query, location) }
}