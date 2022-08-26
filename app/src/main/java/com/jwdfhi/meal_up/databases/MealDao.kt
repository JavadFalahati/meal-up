package com.jwdfhi.meal_up.databases

import androidx.room.*
import com.jwdfhi.meal_up.models.LikedMealModel
import com.jwdfhi.meal_up.models.MarkedMealModel
import com.jwdfhi.meal_up.models.MealModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {

    /**
    * meal_table
    */
    @Query("SELECT * FROM meal_table")
    fun getMeals(): Flow<List<MealModel>>

    @Query("SELECT * FROM meal_table where idMeal =:id")
    suspend fun getMealById(id: String): MealModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(mealModel: MealModel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMeal(mealModel: MealModel)

    @Query("DELETE FROM MEAL_TABLE where idMeal =:id")
    suspend fun deleteMeal(id: String)

    @Query("DELETE from meal_table")
    suspend fun deleteAllMeals()

}