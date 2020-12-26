package com.example.foodtrck.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.foodtrck.data.repository.StreetFoodRepository
import com.example.foodtrck.ui.foodtruck.ARG_FOODTRUCK

class FoodTruckViewModel @ViewModelInject constructor(
    private val repository: StreetFoodRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _foodTruck = savedStateHandle.getLiveData<String>(ARG_FOODTRUCK)
        .switchMap { id ->
            repository.fetchFoodTruck(id)
        }

    val foodTruck = _foodTruck
}
