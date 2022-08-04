package com.jwdfhi.meal_up.services

import com.jwdfhi.meal_up.models.*
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface MealService {

    //  www.themealdb.com/api/json/v1/1/random.php
    @GET("random.php")
    suspend fun getRandomMeal(): RandomMealServiceModel

    //  www.themealdb.com/api/json/v1/1/lookup.php?i=52772
    @GET("lookup.php")
    suspend fun getMealDetail(
        @Query("i") id: String
    ): MealDetailServiceModel

    //  www.themealdb.com/api/json/v1/1/categories.php
    @GET("categories.php")
    suspend fun getMealCategoryList(): MealCategoryListServiceModel

    //  www.themealdb.com/api/json/v1/1/list.php?a=list
    @GET("list.php")
    suspend fun getMealAreaList(
        @Query("a") a: String = "list"
    ): MealAreaListServiceModel

    //  www.themealdb.com/api/json/v1/1/list.php?i=list
    @GET("list.php")
    suspend fun getMealIngredientList(
        @Query("i") i: String = "list"
    ): MealIngredientListServiceModel

    //  www.themealdb.com/api/json/v1/1/filter.php?i=chicken_breast
    @GET("filter.php")
    suspend fun getFilteredMealListByIngredient(
        @Query("i") ingredient: String,
    ): MealListByIngredientServiceModel

    //  www.themealdb.com/api/json/v1/1/filter.php?a=Canadian
    @GET("filter.php")
    suspend fun getFilteredMealListByArea(
        @Query("a") area: String,
    ): MealListByAreaServiceModel

    //  www.themealdb.com/api/json/v1/1/filter.php?c=Seafood
    @GET("filter.php")
    suspend fun getFilteredMealListByCategory(
        @Query("c") category: String,
    ): MealListByCategoryServiceModel

}