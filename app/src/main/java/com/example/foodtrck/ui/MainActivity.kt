package com.example.foodtrck.ui

import android.graphics.Region
import android.os.Bundle
import android.transition.Fade
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import com.example.foodtrck.R
import com.example.foodtrck.ui.foodtruck.FoodTruckFragment
import com.example.foodtrck.ui.foodtruck.FoodtruckImageTransition
import com.example.foodtrck.ui.foodtrucks.FoodTruckListAdapter
import com.example.foodtrck.ui.foodtrucks.FoodTruckListFragment
import com.example.foodtrck.ui.map.FoodTruckMapFragment
import com.example.foodtrck.ui.regions.RegionListAdapter
import com.example.foodtrck.ui.regions.RegionListFragment
import com.google.android.gms.maps.MapFragment
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
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)

        if(savedInstanceState == null)
        {
            transitionRegionFragment()
        }

        navigationView.setOnNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.navigation_regions -> {
                    if(onCurrentFragment(RegionListFragment.TAG)) {
                        return@setOnNavigationItemSelectedListener true
                    }

                    transitionRegionFragment()
                    true
                }
                R.id.navigation_map -> {
                    if(onCurrentFragment(FoodTruckMapFragment.TAG)) {
                        return@setOnNavigationItemSelectedListener true
                    }
                    transitionFoodtruckMapFragment()
                    true
                }
                else -> false
            }
        }
    }

    private fun onCurrentFragment(fragmentTag: String): Boolean {
        val currentFragment = supportFragmentManager.findFragmentByTag(fragmentTag)
        if(currentFragment != null && currentFragment.isVisible) {
            return true
        }
        return false
    }

    private fun transitionRegionFragment() {
        val regionListFragment = RegionListFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_up,
                R.anim.slide_down,
                R.anim.slide_up,
                R.anim.slide_down)
            .replace(R.id.fragment_container, regionListFragment, RegionListFragment.TAG)
            .addToBackStack(null)
            .commit()
    }

    private fun transitionFoodtruckMapFragment() {
        val regionListFragment = FoodTruckMapFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_up,
                R.anim.slide_down,
                R.anim.slide_up,
                R.anim.slide_down)
            .replace(R.id.fragment_container, regionListFragment, FoodTruckMapFragment.TAG)
            .addToBackStack(null)
            .commit()
    }

    override fun onClickRegion(regionName: String) {
        val foodTruckListFragment = FoodTruckListFragment.newInstance(regionName)
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_right,
                R.anim.slide_down,
                R.anim.slide_right,
                R.anim.slide_down)
            .replace(R.id.fragment_container, foodTruckListFragment, FoodTruckListFragment.TAG)
            .addToBackStack(null)
            .commit()
    }

    override fun onClickFoodTruck(foodTruckID: String, view: View?) {
        val foodtruckDetails = FoodTruckFragment.newInstance(foodTruckID)
        foodtruckDetails.sharedElementEnterTransition = FoodtruckImageTransition()
        foodtruckDetails.sharedElementReturnTransition = FoodtruckImageTransition()
        foodtruckDetails.enterTransition = Fade()
        foodtruckDetails.exitTransition = Fade()


//            .setCustomAnimations(
//                R.anim.slide_right,
//                R.anim.slide_left,
//                R.anim.slide_right,
//                R.anim.slide_left)

        supportFragmentManager
            .beginTransaction()
            .addSharedElement(view!!, "details_image_transition")
            .replace(R.id.fragment_container, foodtruckDetails, FoodTruckFragment.TAG)
            .addToBackStack(null)
            .commit()
    }
}