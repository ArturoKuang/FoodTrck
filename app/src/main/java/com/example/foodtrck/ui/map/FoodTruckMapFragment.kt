package com.example.foodtrck.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.foodtrck.databinding.FoodtruckMapFragmentBinding
import com.example.foodtrck.ui.ToolbarFragment
import com.example.foodtrck.utils.autoCleared
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback

class FoodTruckMapFragment:
    ToolbarFragment(),
    OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener {

    private var binding: FoodtruckMapFragmentBinding by autoCleared()
    private lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FoodtruckMapFragmentBinding.inflate(inflater, container, false)
        setToolbar("", false)

        binding.foodtruckMap.onCreate(null)
        binding.foodtruckMap.getMapAsync(this)
        return binding.root
    }

    companion object {
        const val TAG = "FOODTRUCK_MAP_FRAGMENT"

        fun newInstance(): FoodTruckMapFragment {
            return FoodTruckMapFragment()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.foodtruckMap.onResume()
    }

    override fun onStart() {
        super.onStart()
        binding.foodtruckMap.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.foodtruckMap.onStop()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap ?: return
        googleMap.setOnMyLocationButtonClickListener(this)
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        googleMap.isMyLocationEnabled = true
    }

    override fun onPause() {
        binding.foodtruckMap.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        binding.foodtruckMap.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.foodtruckMap.onLowMemory()
    }

    override fun onMyLocationButtonClick(): Boolean {
        return false
    }
}