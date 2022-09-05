package com.jwdfhi.meal_up.models

import androidx.compose.ui.graphics.Color
import com.jwdfhi.meal_up.ui.theme.*

enum class MealCategory {
    BREAKFAST,
    DINNER,
    LAUNCH
}

public fun MealCategory.getName(): String = when (this) {
    MealCategory.BREAKFAST -> "Breakfast"
    MealCategory.LAUNCH -> "Launch"
    MealCategory.DINNER -> "Dinner"
}

public fun MealCategory.getColor(): Color = when (this) {
    MealCategory.BREAKFAST -> Green80Color
    MealCategory.LAUNCH -> Yellow80Color
    MealCategory.DINNER -> Red80Color
}