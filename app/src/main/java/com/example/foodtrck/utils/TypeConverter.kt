package com.example.foodtrck.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

inline fun <reified T> convertToJson(obj: T): String? {
    val gson = Gson()
    return gson.toJson(obj)
}

inline fun <reified T> convertJsonToObj(json: String?): T {
    val gson = Gson()
    return gson.fromJson(json, T::class.java)
}

inline fun <reified T> convertJsonToList(json: String?): List<T> {
    val gson = Gson()
    if (json.isNullOrBlank()) {
        return emptyList()
    }

    return gson.fromJson(json, object : TypeToken<List<T>>() {}.type)
}

fun convertTrimmedDate(value: Long): Date {
    val dateOffSet = 2
    var convertedDate = value

    for (i in 0..dateOffSet) {
        convertedDate *= 10
    }

    return Date(convertedDate)
}
