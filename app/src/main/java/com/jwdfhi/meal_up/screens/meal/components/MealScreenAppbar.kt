package com.jwdfhi.meal_up.screens.meal.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jwdfhi.meal_up.R
import com.jwdfhi.meal_up.models.IngredientWithColorModel
import com.jwdfhi.meal_up.models.MealModel
import com.jwdfhi.meal_up.ui.theme.*
import com.jwdfhi.meal_up.utils.isNullOrEmptyOfServer
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.dw
import com.slaviboy.composeunits.sh

@Composable
fun MealScreenAppbar(
    mealItem: MealModel,
    likeOnTap: () -> Unit,
    backOnTap: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .background(color = White50Color.copy(0.6f))
                    .clickable { backOnTap() }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.left_arrow_icon_1),
                    contentDescription = "Back",
                    colorFilter = ColorFilter.tint(color = White100Color),
                    modifier = Modifier
                        .height(height = 0.055.dh)
                        .padding(8.dp)
                )
            }
            Box(
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .background(color = White100Color)
                    .clickable { likeOnTap() }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.un_fill_like_icon_1),
                    contentDescription = "Like",
                    colorFilter = ColorFilter.tint(color = Black90Color),
                    modifier = Modifier
                        .height(height = 0.055.dh)
                        .padding(8.dp)
                )
            }
        }
    }
}