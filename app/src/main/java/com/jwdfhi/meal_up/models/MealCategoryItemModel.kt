package com.jwdfhi.meal_up.models

import androidx.compose.ui.graphics.Color

data class MealCategoryItemModel(
    val value: MealCategory,
    val backgroundImage: Int,
    val colors: List<Color>
)
