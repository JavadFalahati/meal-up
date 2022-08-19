package com.jwdfhi.meal_up.utils

import com.jwdfhi.meal_up.models.FilterListSelectedItemTextModel
import com.jwdfhi.meal_up.models.FilterType

class FilterListSelectedItemHelper {

    companion object {

        public fun notCheckingPreviousFilteredList(
            filterType: FilterType,
            filterListSelectedItemTextModel: FilterListSelectedItemTextModel
        ): Boolean {
            when (filterType) {
                FilterType.Category -> return true
                FilterType.Ingredient -> if (filterListSelectedItemTextModel.category.isEmpty()) { return true }
                FilterType.Area -> {
                    if (filterListSelectedItemTextModel.category.isEmpty()
                        && filterListSelectedItemTextModel.ingredients.isEmpty()
                    ) { return true }
                }
            }

            return false
        }

        public fun isNotEmpty(filterListSelectedItemTextModel: FilterListSelectedItemTextModel): Boolean {
            return  filterListSelectedItemTextModel.category.isNotEmpty()
                    || filterListSelectedItemTextModel.ingredients.isNotEmpty()
                    || filterListSelectedItemTextModel.area.isNotEmpty()
        }

    }

}