package com.example.foodtrck.ui.map

import android.annotation.SuppressLint
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.bumptech.glide.Glide
import com.example.foodtrck.R
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
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.location_bottom_sheet.view.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import timber.log.Timber

@AndroidEntryPoint
class FoodTruckMapFragment :
    ToolbarFragment(),
    OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener {

    private var binding: FoodtruckMapFragmentBinding by autoCleared()
    private lateinit var map: GoogleMap
    private lateinit var lastLocation: LatLng
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var regionName = MutableLiveData<String>()
    private val regionViewModel: RegionListViewModel by viewModels()
    private val foodtruckViewModel: FoodTrucksViewModel by viewModels()
    private val markerFoodtruckTable = mutableMapOf<Marker, FoodTruck>()

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

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet.body)
        bottomSheetBehavior.addBottomSheetCallback(
            object :
                BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (newState) {
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            setAnimationDrawable(
                                binding.bottomSheet.body.peek,
                                R.drawable.animated_bottom_sheet_peek_down
                            )
                        }
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            setAnimationDrawable(
                                binding.bottomSheet.body.peek,
                                R.drawable.animated_bottom_sheet_peek_up
                            )
                        }
                        else -> {}
                    }
                }
                override fun onSlide(bottomSheet: View, slideOffset: Float) { }
            }
        )
    }

    private fun setAnimationDrawable(view: ImageView, @DrawableRes resID: Int) {
        val animation: AnimatedVectorDrawableCompat? =
            AnimatedVectorDrawableCompat.create(requireContext(), resID)

        animation?.let {
            view.setImageDrawable(it)
            (view.drawable as Animatable?)?.start()
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

    @SuppressLint("MissingPermission")
    @AfterPermissionGranted(RC_LOCATION)
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        requestLocationPermissions(this) {
            map.isMyLocationEnabled = true
            getDeviceLocation()
        }

        map.setOnMarkerClickListener(this)
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

                placeFoodtruckMarkers()
            }
        }
    }

    private fun placeFoodtruckMarkers() {
        queryRegion()?.observe(
            viewLifecycleOwner,
            { result ->
                if (result.status == Resource.Status.SUCCESS) {
                    result.data?.let { foodtruckResponse ->
                        val foodtruckList: List<FoodTruck>? =
                            foodtruckResponse.vendors?.values?.toList()

                        foodtruckList?.forEach { foodtruck ->
                            val openFoodTruckSchedule: ScheduleInfo? = foodtruck.getCurrentSchedule()
                            if (openFoodTruckSchedule != null) {
                                val foodTruckPosition = LatLng(
                                    openFoodTruckSchedule.latitude,
                                    openFoodTruckSchedule.longitude
                                )
                                val marker: Marker = map.addMarker(
                                    MarkerOptions()
                                        .position(foodTruckPosition)
                                        .title(foodtruck.name)
                                )

                                markerFoodtruckTable[marker] = foodtruck
                            }
                        }
                    }
                }
            }
        )
    }

    private fun queryRegion(): LiveData<Resource<FoodTruckResponse>>? {
        return getNearestRegionName().switchMap {
            foodtruckViewModel.setQuery(it)
            foodtruckViewModel.foodTruckList
        }
    }

    private fun getNearestRegionName(): LiveData<String> {
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
            regionName.value = nearestRegion?.id ?: ""
            return@switchMap regionName
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
        private const val DEFAULT_ZOOM = 10f
        private const val SEARCH_RADIUS = 50f // MILES

        fun newInstance(): FoodTruckMapFragment {
            return FoodTruckMapFragment()
        }
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        val foodtruck = markerFoodtruckTable[marker]
        Timber.d("Marker OnClick() $foodtruck")

        binding.bottomSheet.body.visibility = View.VISIBLE
        Glide.with(this)
            .load(foodtruck?.images?.header?.first())
            .placeholder(R.drawable.ic_foodtruck_placeholder)
            .into(binding.bottomSheet.body.food_truck_details_image)

        binding.bottomSheet.body.food_truck_details_phone.text = foodtruck?.phone
        binding.bottomSheet.body.food_truck_details_website.text = foodtruck?.url
        binding.bottomSheet.body.rating.text = foodtruck?.rating.toString()
        binding.bottomSheet.body.food_truck_details_email.text = foodtruck?.email
        binding.bottomSheet.body.food_truck_details_description.text = foodtruck?.description

        return false
    }
}
