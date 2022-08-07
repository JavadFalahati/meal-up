package com.jwdfhi.meal_up.components

import android.graphics.Paint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.jwdfhi.meal_up.ui.theme.Black80Color
import com.jwdfhi.meal_up.ui.theme.Black90Color
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.dw
import com.slaviboy.composeunits.sh

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
    height: Dp = 0.15.dh,
    margin: Dp = 8.dp,
    padding: Dp = 8.dp,
    backgroundColor: androidx.compose.ui.graphics.Color = Color.White,
    borderRadius: Dp = 6.dp
) { // TODO: check the expanded
    Box(
        modifier = Modifier
            .padding(margin)
            .clip(shape = RoundedCornerShape(borderRadius))
            .background(backgroundColor)
            .height(height = height)
            .clickable { onTap() }
    ) {
        Row(
            modifier = Modifier
                .padding(padding)
                .align(Center)
        ) {
            Spacer(modifier = Modifier.weight(0.1f))
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = "Meal",
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(borderRadius))
                    .weight(2.5f, true)
                    .align(CenterVertically)
            )
            Spacer(modifier = Modifier.weight(0.7f))
            Column(
                modifier = Modifier
                    .weight(6f)
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        color = Black90Color,
                        fontWeight = FontWeight.Bold,
                        fontSize = 0.028.sh
                    ),
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row() {
                    ingredientList.forEach { ingredient ->
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .clip(shape = RoundedCornerShape(3.dp))
                                .background(Color(0xFFADADAD))
                        ) {
                            Text(
                                text = "#" + ingredient,
                                style = TextStyle(
                                    color = Black80Color,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 0.014.sh,
                                ),
                                modifier = Modifier
                                    .padding(
                                        horizontal = 5.dp,
                                        vertical = 2.dp
                                    )
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.weight(0.1f))
        }
    }
}