package com.jwdfhi.meal_up.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jwdfhi.meal_up.models.LikedMealModel
import com.jwdfhi.meal_up.models.MarkedMealModel

@Database(entities = [LikedMealModel::class, MarkedMealModel::class], version = 1, exportSchema = false)
abstract class MealDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao
}