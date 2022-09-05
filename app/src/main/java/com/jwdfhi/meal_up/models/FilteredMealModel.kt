package com.jwdfhi.meal_up.models

import com.jwdfhi.meal_up.ui.theme.White100Color

data class FilteredMealModel(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,

    var isLiked: Boolean = false,

    var isMarked: Boolean = false,
    var markCategory: MealCategory? = null,
)