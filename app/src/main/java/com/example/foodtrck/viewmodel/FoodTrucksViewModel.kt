package com.example.foodtrck.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.foodtrck.data.model.FoodTruckResponse
import com.example.foodtrck.data.repository.StreetFoodRepository
import com.example.foodtrck.ui.foodtrucks.ARG_REGION_NAME
import com.example.foodtrck.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FoodTrucksViewModel @ViewModelInject constructor(
    private val repository: StreetFoodRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _foodTruckList = MutableLiveData<Resource<FoodTruckResponse>>()

    val foodTruckList: LiveData<Resource<FoodTruckResponse>> = savedStateHandle
        .getLiveData<String>(ARG_REGION_NAME)
        .switchMap {
        fetchFoodTrucks(it)
        _foodTruckList
    }

    fun fetchFoodTrucks(regionName: String) {
        viewModelScope.launch {
            repository.fetchFoodTrucks(regionName).collect {
                _foodTruckList.value = it
            }
        }
    }
}