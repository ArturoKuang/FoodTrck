package com.example.foodtrck.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.foodtrck.R
import com.example.foodtrck.data.model.FoodTruck
import com.example.foodtrck.ui.foodtruck.FoodTruckFragment
import com.example.foodtrck.ui.foodtrucks.FoodTruckListAdapter
import com.example.foodtrck.ui.foodtrucks.FoodTruckListFragment
import com.example.foodtrck.ui.regions.RegionListAdapter
import com.example.foodtrck.viewmodel.FoodTruckViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity :
    AppCompatActivity(),
    RegionListAdapter.RegionItemListener,
    FoodTruckListAdapter.FoodTruckItemListener {

    lateinit var navigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationView = findViewById(R.id.nav_view)
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