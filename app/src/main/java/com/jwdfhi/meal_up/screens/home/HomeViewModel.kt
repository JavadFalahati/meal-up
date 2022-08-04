package com.jwdfhi.meal_up.screens.home

import com.jwdfhi.meal_up.repositories.MealServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val mealServiceRepository: MealServiceRepository) {



}