package com.jwdfhi.meal_up.screens.management

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.jwdfhi.meal_up.R
import com.jwdfhi.meal_up.models.CustomViewModel
import com.jwdfhi.meal_up.utils.Constant
import com.jwdfhi.meal_up.utils.setAllManagementPrimaryColor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ManagementViewModel @Inject constructor(
    public val context: Context,
) : ViewModel(), CustomViewModel<Nothing, Nothing, Nothing> {

    override fun onBackPressed(navController: NavController) {
        super.onBackPressed(navController)
    }

    public fun setColorToSharedPreferencesAndManagementSettings(color: Color) {
        setColorInSharedPreferences(color)
        color.setAllManagementPrimaryColor()
    }

    private fun setColorInSharedPreferences(color: Color): Unit {
        val editor = context.getSharedPreferences(Constant.MANAGEMENT_SETTING_KEY, Context.MODE_PRIVATE).edit()

        editor.putString(Constant.PRIMARY_COLOR_KEY, color.value.toString())
        editor.commit()
    }

    fun sendEmailToContact(context: Context): Unit {
        val mIntent = Intent(Intent.ACTION_SENDTO)
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        mIntent.data = Uri.parse("mailto:" + "m.javadfalahati@gmail.com")
        // mIntent.type = "text/plain"
        mIntent.putExtra(Intent.EXTRA_EMAIL, "m.javadfalahati@gmail.com")
        mIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.meal_up_issue_or_request)
        mIntent.putExtra(Intent.EXTRA_TEXT, R.string.Hello_Meal_up)

        try {
            startActivity(context, Intent.createChooser(mIntent, ""), Bundle.EMPTY)
        }
        catch (e: Exception) {
            Toast.makeText(context, R.string.An_unexpected_error_accrued, Toast.LENGTH_LONG).show()
        }
    }
}