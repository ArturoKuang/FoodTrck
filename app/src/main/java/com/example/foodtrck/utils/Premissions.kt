package com.example.foodtrck.utils

import android.Manifest
import androidx.fragment.app.Fragment
import pub.devrel.easypermissions.EasyPermissions

const val RC_LOCATION = 0
private const val LOCATION_RATIONALE = "Location permission not granted"

fun requestLocationPermissions(fragment: Fragment, func: () -> Unit) {
    val fineAccessPermission = Manifest.permission.ACCESS_FINE_LOCATION
    val coarseAccessPermission = Manifest.permission.ACCESS_COARSE_LOCATION

    if(EasyPermissions.hasPermissions(fragment.requireContext(), fineAccessPermission) ||
            EasyPermissions.hasPermissions(fragment.requireContext(), coarseAccessPermission)) {
        func.invoke()
    } else {
        EasyPermissions.requestPermissions(
            fragment,
            LOCATION_RATIONALE,
            RC_LOCATION, fineAccessPermission)
    }
}