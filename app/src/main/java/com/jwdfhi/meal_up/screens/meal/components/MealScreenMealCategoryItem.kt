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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jwdfhi.meal_up.models.MealCategoryItemModel
import com.jwdfhi.meal_up.models.getName
import com.jwdfhi.meal_up.ui.theme.White100Color

@Composable
fun MealScreenMealCategoryItem(
    item: MealCategoryItemModel,
    onTap: () -> Unit,
    height: Dp
) {
    val fadedColors = mutableListOf<Color>()
    item.colors.forEach {
        fadedColors.add(it.copy(0.6f))
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = height)
            .clip(shape = RoundedCornerShape(6.dp))
            .clickable { onTap() }
    ) {
        Image(
            painter = painterResource(item.backgroundImage),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .matchParentSize()
        )
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = fadedColors
                    )
                )
                .matchParentSize()
                .align(Alignment.Center)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(),
                text = item.value.getName(),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Normal,
                    color = White100Color
                ),

            )
        }
    }
}