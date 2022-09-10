package com.jwdfhi.meal_up.screens.management

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
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
}