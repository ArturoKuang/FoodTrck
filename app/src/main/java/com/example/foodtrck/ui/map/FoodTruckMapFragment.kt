package com.example.foodtrck.ui.map

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.example.foodtrck.data.model.FoodTruck
import com.example.foodtrck.data.model.FoodTruckResponse
import com.example.foodtrck.data.model.Region
import com.example.foodtrck.data.model.ScheduleInfo
import com.example.foodtrck.databinding.FoodtruckMapFragmentBinding
import com.example.foodtrck.ui.ToolbarFragment
import com.example.foodtrck.utils.*
import com.example.foodtrck.viewmodel.FoodTrucksViewModel
import com.example.foodtrck.viewmodel.RegionListViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import timber.log.Timber

@AndroidEntryPoint
class FoodTruckMapFragment :
    ToolbarFragment(),
    OnMapReadyCallback {

    private var binding: FoodtruckMapFragmentBinding by autoCleared()
    private lateinit var map: GoogleMap
    private lateinit var lastLocation: LatLng
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val regionViewModel: RegionListViewModel by viewModels()
    private val foodtruckViewModel: FoodTrucksViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FoodtruckMapFragmentBinding.inflate(inflater, container, false)

        binding.foodtruckMap.onCreate(null)
        binding.foodtruckMap.getMapAsync(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideToolbar()
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

    @SuppressLint("MissingPermission")
    @AfterPermissionGranted(RC_LOCATION)
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        requestLocationPermissions(this) {
            map.isMyLocationEnabled = true
            getDeviceLocation()
        }

        placeFoodtruckMarkers()
    }


    private fun placeFoodtruckMarkers() {
        //foodtruckViewModel.fetchFoodTrucks("boston")
        foodtruckViewModel.foodTruckList.observe(viewLifecycleOwner, { result ->
            Timber.d("RES MESSAGE ${result.message}")
            if (result.status == Resource.Status.SUCCESS) {
                result.data?.let { foodtruckResponse ->
                    val foodtruckList: List<FoodTruck>? =
                        foodtruckResponse.vendors?.values?.toList()

                    Timber.d("FOODTRUCKLIST ${foodtruckList.toString()}")
                    foodtruckList?.forEach { foodtruck ->
                        val openFoodTruckSchedule: ScheduleInfo? = foodtruck.getCurrentSchedule()
                        if (openFoodTruckSchedule != null) {
                            val foodTruckPosition = LatLng(
                                openFoodTruckSchedule.latitude,
                                openFoodTruckSchedule.longitude
                            )
                            map.addMarker(
                                MarkerOptions()
                                    .position(foodTruckPosition)
                                    .title(foodtruck.name)
                            )
                        }
                    }

                    binding.foodtruckMap.invalidate()
                }
            }
        })
    }

    private fun getFoodtruckList(): LiveData<Resource<FoodTruckResponse>> {
        return regionViewModel.regionList.switchMap { result ->
            var nearestRegion: Region? = null
            if (result.status == Resource.Status.SUCCESS) {
                result.data?.let { list ->
                    val currentLocation =
                        createLocation(lastLocation.latitude, lastLocation.longitude)
                    val nearestRegionList = list.filter { region ->
                        var distance = currentLocation.distanceTo(region.getLocation())
                        distance = convertToRoundedMiles(distance)

                        distance < SEARCH_RADIUS
                    }
                    nearestRegion = nearestRegionList.first()
                }
            }

            foodtruckViewModel.fetchFoodTrucks(nearestRegion?.name ?: "")
            return@switchMap foodtruckViewModel.foodTruckList
        }
    }

    @SuppressLint("MissingPermission")
    private fun getDeviceLocation() {
        val locationResult = fusedLocationProviderClient.lastLocation
        locationResult.addOnCompleteListener(requireActivity()) { task ->
            if (task.isSuccessful) {
                if (task.result != null) {
                    lastLocation = LatLng(task.result.latitude, task.result.longitude)
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(lastLocation, DEFAULT_ZOOM))
                }
            }
        }
    }

    override fun onPause() {
        binding.foodtruckMap.onPause()
        super.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.foodtruckMap.onLowMemory()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {
        const val TAG = "FOODTRUCK_MAP_FRAGMENT"
        private const val DEFAULT_ZOOM = 15f
        private const val SEARCH_RADIUS = 50f //MILES

        fun newInstance(): FoodTruckMapFragment {
            return FoodTruckMapFragment()
        }
    }
}