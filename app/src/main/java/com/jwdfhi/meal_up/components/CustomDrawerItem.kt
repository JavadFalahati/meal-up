package com.jwdfhi.meal_up.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jwdfhi.meal_up.R
import com.jwdfhi.meal_up.models.DrawerItemModel
import com.jwdfhi.meal_up.ui.theme.Black90Color
import com.jwdfhi.meal_up.ui.theme.TransparentColor
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.dw
import com.slaviboy.composeunits.sh

@Composable
fun CustomDrawerItem(
    width: Dp = 1.dw,
    height: Dp = 0.07.dh,
    margin: Dp = 6.dp,
    padding: Dp = 6.dp,
    borderRadius: Dp = 0.dp,
    backgroundColor: Color = TransparentColor,
    item: DrawerItemModel
) {
    Box(
        modifier = Modifier
            .padding(margin)
            .clip(shape = RoundedCornerShape(borderRadius))
            .background(backgroundColor)
            .width(width = width)
            .height(height = height)
            .clickable { item.onTap() }
    ) {
        Row(
            modifier = Modifier
                .padding(padding)
                .align(Alignment.Center),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(0.3f)) {
                Image(
                    painter = painterResource(id = item.icon),
                    contentDescription = "Icon"
                )
            }
            Spacer(modifier = Modifier.weight(0.4f))
            Box(modifier = Modifier.weight(5f)) {
                Text(
                    text = item.title,
                    style = TextStyle(
                        color = Black90Color,
                        fontSize = 0.023.sh
                    ),
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                )
            }
        }
    }

}