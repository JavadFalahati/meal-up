package com.jwdfhi.meal_up.screens.meal.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jwdfhi.meal_up.R
import com.jwdfhi.meal_up.ui.theme.Blue90Color
import com.jwdfhi.meal_up.ui.theme.White100Color
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.sh

@Composable
fun MealScreenContentAction(
    title: String,
    backgroundColor: Color,
    image: Painter,
    onTap: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(4.dp))
            .background(color = backgroundColor)
            .clickable { onTap() }
            .padding(horizontal = 8.dp, vertical = 5.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            painter = image,
            contentDescription = title,
            colorFilter = ColorFilter.tint(color = White100Color),
            modifier = Modifier
                .height(height = 0.025.dh)
                .padding(1.dp)
        )
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = title,
            color = White100Color,
            fontWeight = FontWeight.Normal,
            fontSize = 0.016.sh
        )
    }
}