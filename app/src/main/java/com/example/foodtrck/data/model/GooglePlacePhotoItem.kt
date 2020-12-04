package com.example.foodtrck.data.model

import android.net.Uri
import com.example.foodtrck.BuildConfig

private const val WIDTH = "400"
private const val HEIGHT = "400"

data class GooglePlacePhotoItem(
    val photo_reference: String
) {
    val photoUri: Uri
        get() {
            return Uri.parse("https://maps.googleapis.com/maps/api/place/photo")
                .buildUpon()
                .appendQueryParameter("maxwidth", WIDTH)
                .appendQueryParameter("maxheight", HEIGHT)
                .appendQueryParameter("photoreference ", photo_reference)
                .appendQueryParameter("key", BuildConfig.GOOGLE_PLACE_KEY)
                .build()
        }
}
