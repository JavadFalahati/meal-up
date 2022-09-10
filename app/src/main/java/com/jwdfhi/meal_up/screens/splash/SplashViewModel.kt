package com.jwdfhi.meal_up.screens.splash

import android.content.Context
import android.os.Handler
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.jwdfhi.meal_up.models.CustomViewModel
import com.jwdfhi.meal_up.screens.Screens
import com.jwdfhi.meal_up.utils.Constant
import com.jwdfhi.meal_up.utils.setAllManagementPrimaryColor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    public val context: Context,
) : ViewModel(), CustomViewModel<NavController, Nothing, Nothing> {

    override fun initState(navController: NavController) {
        getColorFromSharedPreferences()

        Handler().postDelayed(
            {
                navigateToHomeScreen(navController)
            },
            3000
        )
    }

    override fun onBackPressed(navController: NavController) {}

    private fun getColorFromSharedPreferences(): Unit {
        val color = context.getSharedPreferences(
            Constant.MANAGEMENT_SETTING_KEY, Context.MODE_PRIVATE
        ).getString(Constant.PRIMARY_COLOR_KEY, null)

        if (!color.isNullOrEmpty()) {
            Color(color.toULong()).setAllManagementPrimaryColor()
        }
    }

    private fun navigateToHomeScreen(navController: NavController) = navController.navigate(Screens.HomeScreen.name) { popUpTo(0) }
}