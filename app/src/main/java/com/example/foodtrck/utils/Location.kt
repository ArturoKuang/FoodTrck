package com.example.foodtrck.utils

import android.location.Location
import kotlin.math.round

val Location.conversionRateMiles: Float
    get() = 1609F

fun Location.convertToMiles(distance: Float): Float {
    return (distance/conversionRateMiles)
}

//2 decimal places
fun Location.convertToRoundedMiles(distance: Float): Double {
    return distance.toDouble().round(2)
}

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}