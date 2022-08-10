package com.jwdfhi.meal_up.models

data class FilterItemModel<T> (
    val title: String,
    val items: List<T>,
    val clearItem: (item: T) -> Unit,
    val clearAllItems: () -> Unit,
)