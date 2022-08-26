package com.jwdfhi.meal_up.utils

import com.jwdfhi.meal_up.models.FilterListSelectedItemModel
import com.jwdfhi.meal_up.models.FilterType

public fun FilterListSelectedItemModel.notCheckingPreviousFilteredList(
    filterType: FilterType,
): Boolean {
    when (filterType) {
        FilterType.Category -> return true
        FilterType.Ingredient -> if (this.category.isEmpty()) { return true }
        FilterType.Area -> {
            if (this.category.isEmpty()
                && this.ingredients.isEmpty()
            ) { return true }
        }
    }

    return false
}

public fun FilterListSelectedItemModel.isNotEmpty(): Boolean {
    return  this.category.isNotEmpty()
            || this.ingredients.isNotEmpty()
            || this.area.isNotEmpty()
}