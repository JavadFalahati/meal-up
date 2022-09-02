package com.jwdfhi.meal_up.utils

import androidx.room.TypeConverter
import com.jwdfhi.meal_up.models.MealCategory

class MealDatabaseConverter {

    @TypeConverter
    fun fromMealCategory(mealCategory: MealCategory?): String {
        return mealCategory?.name ?: ""
    }

    @TypeConverter
    fun toMealCategory(mealCategory: String): MealCategory? {
        return if (mealCategory.isEmpty()) null else MealCategory.valueOf(mealCategory)
    }

}