package com.jwdfhi.meal_up.screens.meal.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.jwdfhi.meal_up.R
import com.jwdfhi.meal_up.models.MealScreenBottomSheetItemSectionModel
import com.jwdfhi.meal_up.ui.theme.White100Color
import com.jwdfhi.meal_up.utils.ManagementSettings
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.dw

@Composable
fun MealScreenBottomSheetItemSection(
    item: MealScreenBottomSheetItemSectionModel
) {

    when (item.splitted) {
        false -> Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(6.dp))
                .background(White100Color)
                .padding(8.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MealScreenBottomSheetItemSectionIcon(icon = item.icon)
                Spacer(modifier = Modifier.width(0.03.dw))
                Text(
                    text = item.title,
                    style = item.titleStyle
                )
            }
            Spacer(modifier = Modifier.height(0.02.dh))
            item.content()
        }
        true -> Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MealScreenBottomSheetItemSectionSplittedContent(
                title = item.firstSplittedTitle,
                icon = item.firstSplittedIcon,
                titleStyle = item.titleStyle
            ) {
                item.firstSplittedContent()
            }
            MealScreenBottomSheetItemSectionSplittedContent(
                title = item.secondSplittedTitle,
                icon = item.secondSplittedIcon,
                titleStyle = item.titleStyle
            ) {
                item.secondSplittedContent()
            }
        }
    }
}

@Composable
private fun MealScreenBottomSheetItemSectionSplittedContent(
    modifier: Modifier = Modifier,
    title: String,
    icon: Int,
    titleStyle: TextStyle,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .width(0.43.dw)
            .clip(shape = RoundedCornerShape(6.dp))
            .background(White100Color)
            .padding(8.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MealScreenBottomSheetItemSectionIcon(icon = icon)
            Spacer(modifier = Modifier.width(0.03.dw))
            Text(
                text = title,
                style = titleStyle
            )
        }
        Spacer(modifier = Modifier.height(0.02.dh))
        content()
    }
}

@Composable
private fun MealScreenBottomSheetItemSectionIcon(icon: Int) {
    Image(
        painter = painterResource(id = icon),
        contentDescription = "",
        colorFilter = ColorFilter.tint(color = ManagementSettings.PrimaryColor),
        modifier = Modifier
            .height(0.026.dh)
    )
}