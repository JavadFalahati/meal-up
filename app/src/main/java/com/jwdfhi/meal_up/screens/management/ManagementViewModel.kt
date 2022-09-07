package com.jwdfhi.meal_up.screens.management

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.jwdfhi.meal_up.models.CustomViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManagementViewModel @Inject constructor(
    public val context: Context,
) : ViewModel(), CustomViewModel<Nothing, Nothing, Nothing> {

    override fun onBackPressed(navController: NavController) {
        super.onBackPressed(navController)
    }
}