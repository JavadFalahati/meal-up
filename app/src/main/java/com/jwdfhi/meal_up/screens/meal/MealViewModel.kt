package com.jwdfhi.meal_up.screens.meal

import android.content.Context
import androidx.lifecycle.ViewModel
import com.jwdfhi.meal_up.repositories.MealServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(
    val context: Context,
    val mealServiceRepository: MealServiceRepository
) : ViewModel() {

    fun initState(id: String): Unit {

    }

}