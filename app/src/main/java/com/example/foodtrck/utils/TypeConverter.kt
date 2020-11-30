package com.example.foodtrck.utils

import com.google.gson.Gson


inline fun<reified T> convertToJson(obj: T): String? {
    val gson = Gson()
    return gson.toJson(obj)
}

inline fun<reified T> convertJsonToObj(json: String?): T {
    val gson = Gson()
    return gson.fromJson(json, T::class.java)
}
