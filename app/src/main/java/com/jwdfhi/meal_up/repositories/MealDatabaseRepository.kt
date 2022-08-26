package com.jwdfhi.meal_up.repositories

import com.jwdfhi.meal_up.databases.MealDao
import com.jwdfhi.meal_up.models.LikedMealModel
import com.jwdfhi.meal_up.models.MarkedMealModel
import com.jwdfhi.meal_up.models.MealModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MealDatabaseRepository @Inject constructor(private val mealDao: MealDao) {

    /**
    * liked_meal_repository
    */
    fun getMeals(): Flow<List<MealModel>> = mealDao.getMeals()

    suspend fun getMealById(id: String): MealModel? = mealDao.getMealById(id = id)

    suspend fun insertMeal(mealModel: MealModel) = mealDao.insertMeal(mealModel = mealModel)

    suspend fun updateMeal(mealModel: MealModel) = mealDao.updateMeal(mealModel = mealModel)

    suspend fun deleteMeal(id: String) = mealDao.deleteMeal(id = id)

    suspend fun deleteAllMeals() = mealDao.deleteAllMeals()
}