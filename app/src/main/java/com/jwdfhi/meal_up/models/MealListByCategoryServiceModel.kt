package com.jwdfhi.meal_up.models

data class MealListByCategoryServiceModel(
    val meals: List<Meal>
) {
    data class Meal(
        val idMeal: String,
        val strMeal: String,
        val strMealThumb: String
    )
}