package com.jwdfhi.meal_up.models

data class FilteredMealModel(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,

    var isLiked: Boolean = false,

    var isMarked: Boolean = false,
    var markColor: String = "",
    var markName: String = "",
)