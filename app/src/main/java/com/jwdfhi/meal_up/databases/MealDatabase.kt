package com.jwdfhi.meal_up.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jwdfhi.meal_up.models.LikedMealModel
import com.jwdfhi.meal_up.models.MarkedMealModel
import com.jwdfhi.meal_up.models.MealModel

@Database(entities = [MealModel::class], version = 6, exportSchema = false)
abstract class MealDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao
}