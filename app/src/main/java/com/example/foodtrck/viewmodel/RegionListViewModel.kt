package com.example.foodtrck.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.foodtrck.data.model.Region
import com.example.foodtrck.data.repository.StreetFoodRepository
import com.example.foodtrck.utils.Resource

class RegionListViewModel @ViewModelInject constructor(
    private val repository: StreetFoodRepository
) : ViewModel() {

    //val characters: LiveData<Resource<List<Region>>> = repository.getRegions()
}