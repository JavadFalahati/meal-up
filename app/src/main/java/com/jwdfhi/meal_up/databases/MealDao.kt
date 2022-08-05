package com.jwdfhi.meal_up.databases

import androidx.room.*
import com.jwdfhi.meal_up.models.LikedMealModel
import com.jwdfhi.meal_up.models.MarkedMealModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {

    /**
    * liked_meal_table
    */
    @Query("SELECT * FROM liked_meal_table")
    fun getLikedMeals(): Flow<List<LikedMealModel>>

    @Query("SELECT * FROM liked_meal_table where idMeal =:id")
    suspend fun getLikedMealById(id: String): LikedMealModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLikedMeal(likedMealModel: LikedMealModel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateLikedMeal(likedMealModel: LikedMealModel)

    @Delete()
    suspend fun deleteLikedMeal(likedMealModel: LikedMealModel)

    @Query("DELETE from liked_meal_table")
    suspend fun deleteAllLikedMeals()

    /**
     * marked_meal_table
     */
    @Query("SELECT * FROM marked_meal_table")
    fun getMarkedMeals(): Flow<List<MarkedMealModel>>

    @Query("SELECT * FROM marked_meal_table where idMeal =:id")
    suspend fun getMarkedMealById(id: String): MarkedMealModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarkedMeal(markedMealModel: MarkedMealModel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMarkedMeal(markedMealModel: MarkedMealModel)

    @Delete()
    suspend fun deleteMarkedMeal(markedMealModel: MarkedMealModel)

    @Query("DELETE from marked_meal_table")
    suspend fun deleteAllMarkedMeals()

}