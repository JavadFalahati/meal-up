package com.jwdfhi.meal_up.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.jwdfhi.meal_up.R
import com.jwdfhi.meal_up.ui.theme.Black80Color
import com.slaviboy.composeunits.sh

data class MealScreenBottomSheetItemSectionModel(
    val title: Int,
    val titleStyle: TextStyle = TextStyle(color = Black80Color,
        fontWeight = FontWeight.Bold,
        fontSize = 0.026.sh
    ),
    val icon: Int,
    val splitted: Boolean = false,
    val firstSplittedTitle: Int = R.string.Empty_String,
    val firstSplittedIcon: Int = R.drawable.food_recipe_icon_1,
    val firstSplittedContent: @Composable () -> Unit = {},
    val secondSplittedTitle: Int = R.string.Empty_String,
    val secondSplittedIcon: Int = R.drawable.food_recipe_icon_1,
    val secondSplittedContent: @Composable () -> Unit = {},
    val content: @Composable () -> Unit,
)
