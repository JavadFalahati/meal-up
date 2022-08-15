package com.jwdfhi.meal_up.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow

data class FilterListItemModel(
    var categories: SnapshotStateList<MealCategoryListServiceModel.Category>,
    var ingredients: SnapshotStateList<MealIngredientListServiceModel.Meal>,
    var areas: SnapshotStateList<MealAreaListServiceModel.Meal>,
)
