package com.example.foodtrck.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.foodtrck.R
import com.example.foodtrck.ui.foodtruck.FoodTruckFragment
import com.example.foodtrck.ui.foodtrucks.FoodTruckListAdapter
import com.example.foodtrck.ui.foodtrucks.FoodTruckListFragment
import com.example.foodtrck.ui.regions.RegionListAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

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
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false);
    }

    override fun onClickRegion(regionName: String) {
        val fragment = FoodTruckListFragment.newInstance(regionName)
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_right,
                R.anim.slide_down,
                R.anim.slide_right,
                R.anim.slide_down)
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onClickFoodTruck(foodTruckID: String) {
        val fragment = FoodTruckFragment.newInstance(foodTruckID)
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_right,
                R.anim.slide_left,
                R.anim.slide_right,
                R.anim.slide_left)
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}