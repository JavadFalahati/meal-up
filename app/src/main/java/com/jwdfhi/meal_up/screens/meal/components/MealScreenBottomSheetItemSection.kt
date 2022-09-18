package com.jwdfhi.meal_up.screens.meal.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.jwdfhi.meal_up.models.MealScreenBottomSheetItemSectionModel
import com.jwdfhi.meal_up.ui.theme.White100Color
import com.slaviboy.composeunits.dw

@Composable
fun MealScreenBottomSheetItemSection(
    item: MealScreenBottomSheetItemSectionModel
) {
    val modifier = Modifier
        .fillMaxWidth()
        .clip(shape = RoundedCornerShape(6.dp))
        .background(White100Color)
        .padding(8.dp)

    when (item.splitted) {
        false -> Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = item.title,
                style = item.titleStyle
            )
            item.content()
        }
        true -> Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = modifier,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = item.title,
                    style = item.titleStyle
                )
                Spacer(modifier = Modifier.width(0.05.dw))
                item.firstSplittedContent()
            }
            MealScreenBottomSheetItemSectionSplittedContent(
                modifier = modifier,
                item = item,
            ) {
                item.firstSplittedContent()
            }
            MealScreenBottomSheetItemSectionSplittedContent(
                modifier = modifier,
                item = item,
            ) {
                item.secondSplittedContent()
            }
        }
    }
}

@Composable
private fun MealScreenBottomSheetItemSectionSplittedContent(
    modifier: Modifier,
    item: MealScreenBottomSheetItemSectionModel,
    content: @Composable () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = item.title,
            style = item.titleStyle
        )
        Spacer(modifier = Modifier.width(0.05.dw))
        content()
    }
}