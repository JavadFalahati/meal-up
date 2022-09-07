package com.jwdfhi.meal_up.screens.management.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jwdfhi.meal_up.R
import com.jwdfhi.meal_up.ui.theme.Black80Color
import com.jwdfhi.meal_up.ui.theme.Black90Color
import com.jwdfhi.meal_up.ui.theme.White100Color
import com.jwdfhi.meal_up.ui.theme.White50Color
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.dw
import com.slaviboy.composeunits.sh

@Composable
fun ManagementScreenManagementItem(
    title: String,
    image: Int,
    color: Color,
    verticalPadding: Dp = 0.02.dh,
    onTap: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = verticalPadding),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .background(color = color.copy(0.2f))
            ) {
                Image(
                    painter = painterResource(id = image),
                    contentDescription = title,
                    colorFilter = ColorFilter.tint(color = color),
                    modifier = Modifier
                        .height(height = 0.058.dh)
                        .padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.width(0.05.dw))
            Text(
                text = title,
                style = TextStyle(
                    color = Black80Color,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.W500,
                    fontSize = 0.023.sh
                )
            )
        }
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(6.dp))
                .background(color = Black80Color.copy(0.2f))
                .clickable { onTap() }
        ) {
            Image(
                painter = painterResource(id = R.drawable.back_right_icon_2),
                contentDescription = title,
                colorFilter = ColorFilter.tint(color = Black80Color),
                modifier = Modifier
                    .height(height = 0.056.dh)
                    .padding(8.dp)
            )
        }
    }
}