package com.jwdfhi.meal_up.screens.meal.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.jwdfhi.meal_up.models.MealModel
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.dw

@Composable
fun MealScreenBackground(mealItem: MealModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                mealItem.strMealThumb,
                contentScale = ContentScale.Crop,
            ),
            contentDescription = "BackgroundImage",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                // .fillMaxWidth()
                .width(1.dw)
                .height(0.43.dh)
                .clip(
                    shape = RoundedCornerShape(
                        bottomStart = 18.dp,
                        bottomEnd = 18.dp,
                    )
                )
        )
    }
}