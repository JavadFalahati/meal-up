package com.jwdfhi.meal_up.screens.filter.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jwdfhi.meal_up.R
import com.jwdfhi.meal_up.ui.theme.Black60Color
import com.jwdfhi.meal_up.ui.theme.Black80Color
import com.jwdfhi.meal_up.ui.theme.Black90Color
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.sh

@Composable
fun FilterAppbar(
    navController: NavController,
    margin: Dp = 0.dp,
    onClear: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxWidth()
            .padding(margin)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(6.dp))
                    .clickable(
                        // interactionSource = remember { MutableInteractionSource() },
                        // indication = null
                    ) { navController.popBackStack() }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.back_left_icon_2),
                    contentDescription = "Back",
                    colorFilter = ColorFilter.tint(color = Black90Color),
                    modifier = Modifier
                        .height(height = 0.055.dh)
                        .padding(8.dp)
                )
            }
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(6.dp))
                    .clickable { onClear() }
                    .padding(
                        horizontal = 8.dp,
                        vertical = 4.dp,
                    ),
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(height = 0.024.dh)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.close_icon_2),
                        contentDescription = "Clear",
                        colorFilter = ColorFilter.tint(color = Black80Color)
                    )
                    Spacer(modifier = Modifier.width(7.dp))
                    Text(
                        text = "Clear",
                        style = TextStyle(
                            color = Black80Color,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Normal,
                            fontSize = 0.018.sh
                        )
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(0.03.dh))
        Text(
            text = "Filters",
            style = TextStyle(
                color = Black90Color,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.W600,
                fontSize = 0.034.sh
            )
        )
    }
}