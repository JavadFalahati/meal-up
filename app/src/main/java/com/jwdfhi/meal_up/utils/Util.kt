package com.jwdfhi.meal_up.utils

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.jwdfhi.meal_up.R


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

public fun String.ifIsEmptyReplaceItWith(value: String): String {
    return when (this) {
        "" -> value
        else -> this
    }
}

public fun openPrivacyAndPolicyWebsite(context: Context) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://pages.flycricket.io/meal-up/privacy.html"))

    try {
        startActivity(context, browserIntent, Bundle.EMPTY)
    }
    catch (e: Exception) {
        Toast.makeText(context, R.string.An_unexpected_error_accrued, Toast.LENGTH_LONG).show()
    }
}