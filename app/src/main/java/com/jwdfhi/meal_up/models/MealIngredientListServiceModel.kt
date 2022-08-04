package com.jwdfhi.meal_up.models

data class MealIngredientListServiceModel(
    val meals: List<Meal>
) {
    data class Meal(
        val idIngredient: String,
        val strDescription: String,
        val strIngredient: String,
        val strType: String
    )
}