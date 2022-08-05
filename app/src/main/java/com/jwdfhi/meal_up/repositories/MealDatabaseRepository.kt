package com.jwdfhi.meal_up.repositories

import com.jwdfhi.meal_up.databases.MealDao
import com.jwdfhi.meal_up.models.LikedMealModel
import com.jwdfhi.meal_up.models.MarkedMealModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MealDatabaseRepository @Inject constructor(private val mealDao: MealDao) {

    /**
    * liked_meal_repository
    */
    fun getLikedMeals(): Flow<List<LikedMealModel>> = mealDao.getLikedMeals()

    suspend fun getLikedMealById(id: String): LikedMealModel = mealDao.getLikedMealById(id = id)

    suspend fun insertLikedMeal(likedMealModel: LikedMealModel) = mealDao.insertLikedMeal(likedMealModel = likedMealModel)

    suspend fun updateLikedMeal(likedMealModel: LikedMealModel) = mealDao.updateLikedMeal(likedMealModel = likedMealModel)

    suspend fun deleteLikedMeal(likedMealModel: LikedMealModel) = mealDao.deleteLikedMeal(likedMealModel = likedMealModel)

    suspend fun deleteAllLikedMeals() = mealDao.deleteAllLikedMeals()

    /**
     * marked_meal_repository
     */
    fun getMarkedMeals(): Flow<List<MarkedMealModel>> = mealDao.getMarkedMeals()

    suspend fun getMarkedMealById(id: String): MarkedMealModel = mealDao.getMarkedMealById(id = id)

    suspend fun insertMarkedMeal(markedMealModel: MarkedMealModel) = mealDao.insertMarkedMeal(markedMealModel = markedMealModel)

    suspend fun updateMarkedMeal(markedMealModel: MarkedMealModel) = mealDao.updateMarkedMeal(markedMealModel = markedMealModel)

    suspend fun deleteMarkedMeal(markedMealModel: MarkedMealModel) = mealDao.deleteMarkedMeal(markedMealModel = markedMealModel)

    suspend fun deleteAllMarkedMeals() = mealDao.deleteAllMarkedMeals()
}