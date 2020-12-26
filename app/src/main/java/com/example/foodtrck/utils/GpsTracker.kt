package com.example.foodtrck.utils

import android.Manifest
import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import androidx.core.app.ActivityCompat
import java.lang.Exception

private const val MIN_DISTANCE_CHANGE_FOR_UPDATES: Float = 10f
private const val MIN_TIME_BW_UPDATES: Long = 1000 * 60 * 1 // 1 minute
private const val LOCATION_NETWORK_REQUEST_CODE = 101

class GpsTracker(val context: Context) : Service(), LocationListener {
    var isGPSEnabled = false
        private set

    var isNetworkEnabled = false
        private set

    var canGetLocation = false
        private set

    var location: Location? = null
        private set

    private val locationManager by lazy {
        context.getSystemService(LOCATION_SERVICE) as LocationManager
    }

    fun getCurrentLocation(): Location? {
        try {
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (isGPSEnabled || isNetworkEnabled) {
                canGetLocation = true
                if (isNetworkEnabled) {
                    checkPermission()
                    locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES,
                        this
                    )

                    location = locationManager.getLastKnownLocation(
                        LocationManager.NETWORK_PROVIDER
                    )
                }

                if (isGPSEnabled) {
                    checkPermission()
                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES,
                        this
                    )

                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return location
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                (context as Activity?)!!,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                LOCATION_NETWORK_REQUEST_CODE
            )
        }
    }

    public fun stopGPS() {
        locationManager.removeUpdates(this)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onLocationChanged(location: Location) {}

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
}
