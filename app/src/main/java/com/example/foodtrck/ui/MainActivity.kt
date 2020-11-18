package com.example.foodtrck.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.example.foodtrck.R
import com.example.foodtrck.data.model.FoodTruck
import com.example.foodtrck.ui.foodtruck.FoodTruckFragment
import com.example.foodtrck.ui.foodtrucks.FoodTruckListAdapter
import com.example.foodtrck.ui.foodtrucks.FoodTruckListFragment
import com.example.foodtrck.ui.regions.RegionListAdapter
import com.example.foodtrck.viewmodel.FoodTruckViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity :
    BaseActivity(),
    RegionListAdapter.RegionItemListener,
    FoodTruckListAdapter.FoodTruckItemListener {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getBottomNavigationMenuItemId(): Int {
        return R.id.navigation_FoodTruckList
    }

    override fun onClickRegion(regionName: String) {
        val fragment = FoodTruckListFragment.newInstance(regionName)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onClickFoodTruck(foodTruckID: String) {
        val fragment = FoodTruckFragment.newInstance(foodTruckID)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}