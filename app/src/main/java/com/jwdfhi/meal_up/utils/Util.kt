package com.jwdfhi.meal_up.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.jwdfhi.meal_up.models.MealCategory

fun deviceHaveConnection(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

    if (capabilities != null) {
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) { return true }
        else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) { return true }
        else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) { return true }
    }

    return false
}

public fun String?.isNullOrEmptyOfServer(): Boolean {
    if (this == null) { return true }
    if (this.isEmpty()) { return true }
    if (this == "null") { return true }
    if (this == "Null") { return true }

    return false
}

public fun MealCategory.getName(): String = when (this) {
    MealCategory.BREAKFAST -> "Breakfast"
    MealCategory.LAUNCH -> "Launch"
    MealCategory.DINNER -> "Dinner"
}