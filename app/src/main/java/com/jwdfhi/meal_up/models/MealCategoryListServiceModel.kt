package com.jwdfhi.meal_up.models

data class MealCategoryListServiceModel(
    val categories: List<Category>
) {
    data class Category(
        var isSelected: Boolean = false,
        val idCategory: String,
        val strCategory: String,
        val strCategoryDescription: String,
        val strCategoryThumb: String
    )
}