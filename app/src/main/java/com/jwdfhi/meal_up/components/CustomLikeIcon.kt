package com.jwdfhi.meal_up.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jwdfhi.meal_up.R
import com.jwdfhi.meal_up.models.MealModel
import com.jwdfhi.meal_up.ui.theme.Black90Color
import com.jwdfhi.meal_up.ui.theme.Red80Color
import com.jwdfhi.meal_up.ui.theme.White100Color
import com.slaviboy.composeunits.dh

@Composable
fun CustomLikeIcon(
    isLiked: Boolean,
    onTap: () -> Unit,
    height: Dp = 0.055.dh,
    iconPadding: Dp = 8.dp,
) {
    Box(
        modifier = Modifier
            .clip(shape = CircleShape)
            .background(color = White100Color)
            .clickable { onTap() }
    ) {
        Image(
            painter = painterResource(
                id = if (isLiked) R.drawable.fill_like_icon_1 else R.drawable.un_fill_like_icon_1
            ),
            contentDescription = "Like",
            colorFilter = ColorFilter.tint(
                color = if (isLiked) Red80Color else Black90Color
            ),
            modifier = Modifier
                .height(height = height)
                .padding(iconPadding)
        )
    }
}