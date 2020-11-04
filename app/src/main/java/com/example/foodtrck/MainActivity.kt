package com.example.foodtrck

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        val navView: BottomNavigationView = findViewById(R.id.nav_view)
//
//
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(setOf(
//                R.id.navigation_FoodTruckList, R.id.navigation_map))
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getBottomNavigationMenuItemId(): Int {
        return R.id.navigation_FoodTruckList
    }
}