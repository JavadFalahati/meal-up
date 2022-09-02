package com.jwdfhi.meal_up.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jwdfhi.meal_up.models.LikedMealModel
import com.jwdfhi.meal_up.models.MarkedMealModel
import com.jwdfhi.meal_up.models.MealModel
import com.jwdfhi.meal_up.utils.MealDatabaseConverter

@Database(entities = [MealModel::class], version = 7, exportSchema = false)
@TypeConverters(MealDatabaseConverter::class)
abstract class MealDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao
}