package com.jwdfhi.meal_up.models

import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.serialization.Serializable

@Serializable
data class FilterListSelectedItemTextModel(
    var category: String = "",
    var ingredients: MutableList<String> = mutableListOf(),
    var area: String = "",
    val receivingSequence: List<FilterType> = listOf(
        FilterType.Category,
        FilterType.Ingredient,
        FilterType.Area
    )
)
