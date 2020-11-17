package com.example.foodtrck.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.foodtrck.data.model.FoodTruck
import com.example.foodtrck.data.model.Region
import com.example.foodtrck.data.repository.StreetFoodRepository
import com.example.foodtrck.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FoodTrucksViewModel @ViewModelInject constructor(
    private val repository: StreetFoodRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        const val KEY = "region_name"
    }

    private val _foodTruckList = MutableLiveData<Resource<List<FoodTruck>>>()

    val foodTruckList: LiveData<Resource<List<FoodTruck>>> = savedStateHandle
        .getLiveData<String>(KEY)
        .switchMap {
        fetchFoodTrucks(it)
        _foodTruckList
    }

    private fun fetchFoodTrucks(_regionName: String) {
        viewModelScope.launch {
            repository.fetchFoodTrucks(_regionName).collect {
                _foodTruckList.value = it
            }
        }
    }
}