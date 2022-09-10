package com.jwdfhi.meal_up.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.ui.graphics.Color
import com.jwdfhi.meal_up.models.MealCategory
import com.jwdfhi.meal_up.ui.theme.Green60Color
import com.jwdfhi.meal_up.ui.theme.Red60Color
import com.jwdfhi.meal_up.ui.theme.Yellow60Color

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

public fun Color.setAllManagementPrimaryColor() {
    ManagementSettings.PrimaryColor = this
    ManagementSettings.Primary80Color = this.copy(0.8f)
    ManagementSettings.Primary60Color = this.copy(0.6f)
    ManagementSettings.LightPrimaryColor = this.copy(0.3f)
}