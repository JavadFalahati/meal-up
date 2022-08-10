package com.jwdfhi.meal_up.models

data class MealAreaListServiceModel(
    val meals: List<Meal>
) {
    data class Meal(
        var isSelected: Boolean = false,
        val strArea: String
    )
}