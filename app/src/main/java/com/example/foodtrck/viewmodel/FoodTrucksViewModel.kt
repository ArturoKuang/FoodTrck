package com.example.foodtrck.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.foodtrck.data.model.FoodTruckResponse
import com.example.foodtrck.data.repository.StreetFoodRepository
import com.example.foodtrck.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

const val ARG_REGION_NAME = "arg_region_name"

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

    private fun fetchFoodTrucks(regionName: String) {
        viewModelScope.launch {
            repository.fetchFoodTrucks(regionName).collect {
                _foodTruckList.value = it
            }
        }
    }

    fun setQuery(regionName: String) {
        savedStateHandle[ARG_REGION_NAME] = regionName
    }
}
