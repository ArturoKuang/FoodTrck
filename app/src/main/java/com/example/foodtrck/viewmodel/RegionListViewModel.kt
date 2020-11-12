package com.example.foodtrck.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodtrck.data.model.Region
import com.example.foodtrck.data.repository.StreetFoodRepository
import com.example.foodtrck.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RegionListViewModel @ViewModelInject constructor(
    private val repository: StreetFoodRepository
) : ViewModel() {

    private val _regionList = MutableLiveData<Resource<List<Region>>>()
    val regionList: LiveData<Resource<List<Region>>> = _regionList

    init {
        fetchRegions()
    }

    private fun fetchRegions() {
        viewModelScope.launch {
            repository.fetchRegions().collect {
                _regionList.value = it
            }
        }
    }
}