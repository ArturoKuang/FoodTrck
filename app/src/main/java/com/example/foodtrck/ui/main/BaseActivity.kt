package com.example.foodtrck.ui.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.foodtrck.R
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val TAG = "BaseActivity"

abstract class BaseActivity: AppCompatActivity() {

    open lateinit var navigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())

        navigationView = findViewById(R.id.nav_view)
        navigationView.apply {
            setOnNavigationItemReselectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.navigation_FoodTruckList -> Log.d(TAG, "list")
                    R.id.navigation_map -> Log.d(TAG, "map")
                }
            }
        }
    }

    // Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }

    abstract fun getLayoutId(): Int

    abstract fun getBottomNavigationMenuItemId(): Int
}