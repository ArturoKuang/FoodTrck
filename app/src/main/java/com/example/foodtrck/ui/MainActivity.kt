package com.example.foodtrck.ui

import android.os.Bundle
import com.example.foodtrck.R
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getBottomNavigationMenuItemId(): Int {
        return R.id.navigation_FoodTruckList
    }
}