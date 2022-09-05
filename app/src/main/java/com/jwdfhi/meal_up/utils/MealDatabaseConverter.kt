package com.jwdfhi.meal_up.utils

import androidx.compose.ui.graphics.Color
import androidx.room.TypeConverter
import com.jwdfhi.meal_up.models.MealCategory

class MealDatabaseConverter {

    @TypeConverter
    fun fromMealCategory(mealCategory: MealCategory?): String? {
        return if (mealCategory != null) mealCategory.name else null
    }

    @TypeConverter
    fun toMealCategory(mealCategory: String?): MealCategory? {
        return if (mealCategory != null) MealCategory.valueOf(mealCategory) else null
    }
}

