package com.jwdfhi.meal_up.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.slaviboy.composeunits.dw

@Composable
fun MealItem(
    expanded: Boolean = false,
    onTap: () -> Unit,
    title: String,
    imageUrl: String,
    ingredientList: List<String>,
    isMarked: Boolean = false,
    markColor: Int = android.graphics.Color.TRANSPARENT,
    isLiked: Boolean = false,
    padding: Dp = 8.dp,
    backgroundColor: androidx.compose.ui.graphics.Color = Color.White,
    borderRadius: Dp = 6.dp
) { // TODO: check the expanded
    Row(
        modifier = Modifier
            .padding(padding)
            .clip(shape = RoundedCornerShape(borderRadius))
            .background(backgroundColor)
            .clickable { onTap() }
    ) {
        Image( // TODO: fix this bug: image no not showing
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = "Meal",
            modifier = Modifier
                .clip(shape = RoundedCornerShape(borderRadius))
                .weight(3f)
                // .size(0.4.dw)
        )
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier
                .weight(6f)
        ) {
            Text(
                text = title
            )
            Spacer(modifier = Modifier.height(2.dp))
            ingredientList.forEach {
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .background(Color(0xFF919191))
                ) {
                    Text(
                        text = it
                    )
                }
            }
        }

    }
}