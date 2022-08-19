package com.jwdfhi.meal_up.utils

import com.jwdfhi.meal_up.models.FilterListSelectedItemModel
import com.jwdfhi.meal_up.models.FilterType

class FilterListSelectedItemHelper {

    companion object {

        public fun notCheckingPreviousFilteredList(
            filterType: FilterType,
            filterListSelectedItemModel: FilterListSelectedItemModel
        ): Boolean {
            when (filterType) {
                FilterType.Category -> return true
                FilterType.Ingredient -> if (filterListSelectedItemModel.category.isEmpty()) { return true }
                FilterType.Area -> {
                    if (filterListSelectedItemModel.category.isEmpty()
                        && filterListSelectedItemModel.ingredients.isEmpty()
                    ) { return true }
                }
            }

            return false
        }

        public fun isNotEmpty(filterListSelectedItemModel: FilterListSelectedItemModel): Boolean {
            return  filterListSelectedItemModel.category.isNotEmpty()
                    || filterListSelectedItemModel.ingredients.isNotEmpty()
                    || filterListSelectedItemModel.area.isNotEmpty()
        }

    }

}