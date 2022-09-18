package com.jwdfhi.meal_up.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.jwdfhi.meal_up.ui.theme.Black80Color
import com.slaviboy.composeunits.sh

data class MealScreenBottomSheetItemSectionModel(
    val title: String,
    val titleStyle: TextStyle = TextStyle(color = Black80Color,
        fontWeight = FontWeight.Bold,
        fontSize = 0.029.sh
    ),
    val splitted: Boolean = false,
    val firstSplittedTitle: String = "",
    val firstSplittedContent: @Composable () -> Unit = {},
    val secondSplittedTitle: String = "",
    val secondSplittedContent: @Composable () -> Unit = {},
    val content: @Composable () -> Unit,
)
