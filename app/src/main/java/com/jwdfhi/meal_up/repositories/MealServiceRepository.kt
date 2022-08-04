package com.jwdfhi.meal_up.repositories

import com.jwdfhi.meal_up.models.*
import com.jwdfhi.meal_up.services.MealService
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

class MealServiceRepository @Inject constructor(private val mealService: MealService) {

    // todo => write exception handler

    suspend fun getRandomMeal(): DataOrException<RandomMealServiceModel, Boolean, Exception> {
        val response = try {
            mealService.getRandomMeal()
        } catch(exception: Exception) {
            return DataOrException(exception = exception)
        }

        return DataOrException(data = response)
    }

    suspend fun getMealDetail(id: String): DataOrException<MealDetailServiceModel, Boolean, Exception> {
        val response = try {
            mealService.getMealDetail(id = id)
        } catch(exception: Exception) {
            return DataOrException(exception = exception)
        }

        return DataOrException(data = response)
    }

    suspend fun getMealCategoryList(): DataOrException<MealCategoryListServiceModel, Boolean, Exception> {
        val response = try {
            mealService.getMealCategoryList()
        } catch(exception: Exception) {
            return DataOrException(exception = exception)
        }

        return DataOrException(data = response)
    }

    suspend fun getMealAreaList(): DataOrException<MealAreaListServiceModel, Boolean, Exception> {
        val response = try {
            mealService.getMealAreaList()
        } catch(exception: Exception) {
            return DataOrException(exception = exception)
        }

        return DataOrException(data = response)
    }

    suspend fun getMealIngredientList(): DataOrException<MealIngredientListServiceModel, Boolean, Exception> {
        val response = try {
            mealService.getMealIngredientList()
        } catch(exception: Exception) {
            return DataOrException(exception = exception)
        }

        return DataOrException(data = response)
    }

    suspend fun getFilteredMealListByIngredient(
        ingredient: String
    ): DataOrException<MealListByIngredientServiceModel, Boolean, Exception> {
        val response = try {
            mealService.getFilteredMealListByIngredient(ingredient = ingredient)
        } catch(exception: Exception) {
            return DataOrException(exception = exception)
        }

        return DataOrException(data = response)
    }

    suspend fun getFilteredMealListByArea(
        area: String
    ): DataOrException<MealListByAreaServiceModel, Boolean, Exception> {
        val response = try {
            mealService.getFilteredMealListByArea(area = area)
        } catch(exception: Exception) {
            return DataOrException(exception = exception)
        }

        return DataOrException(data = response)
    }

    suspend fun getFilteredMealListByCategory(
        category: String
    ): DataOrException<MealListByCategoryServiceModel, Boolean, Exception> {
        val response = try {
            mealService.getFilteredMealListByCategory(category = category)
        } catch(exception: Exception) {
            return DataOrException(exception = exception)
        }

        return DataOrException(data = response)
    }

}