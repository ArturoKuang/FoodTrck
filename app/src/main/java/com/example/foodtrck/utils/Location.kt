package com.example.foodtrck.utils

import android.location.Location
import android.location.LocationManager
import kotlin.math.round

fun createLocation(latitude: Double, longitude: Double): Location {
    val location = Location(LocationManager.GPS_PROVIDER)
    location.latitude = latitude
    location.longitude = longitude
    return location
}

val Location.conversionRateMiles: Float
    get() = 1609F

fun Location.convertToMiles(distance: Float): Float {
    return (distance/conversionRateMiles)
}

//2 decimal places
fun Location.convertToRoundedMiles(distance: Float): Float {
    return convertToMiles(distance).toDouble().round(2).toFloat()
}

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}