package com.jwdfhi.meal_up.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList

data class FilterItemModel<T> (
    val title: String,
    val items: SnapshotStateList<T>,
    val selectItem: (item: T) -> Unit,
    val clearItem: (item: T) -> Unit,
    val clearAllItems: () -> Unit,
)