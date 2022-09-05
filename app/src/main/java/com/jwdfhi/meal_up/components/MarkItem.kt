package com.jwdfhi.meal_up.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jwdfhi.meal_up.R
import com.jwdfhi.meal_up.models.FilteredMealModel
import com.jwdfhi.meal_up.models.MealModel
import com.jwdfhi.meal_up.models.getColor
import com.jwdfhi.meal_up.models.getName
import com.jwdfhi.meal_up.ui.theme.TransparentColor
import com.jwdfhi.meal_up.ui.theme.White100Color
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.dw
import com.slaviboy.composeunits.sh

@Composable
fun <T> MarkItem(
    item: T,
    backgroundColor: Color = White100Color
) {
    var markColor: Color = TransparentColor
    var markName: String = ""
    when (item) {
        is MealModel -> {
            markColor = item.markCategory?.getColor()!!
            markName = item.markCategory?.getName()!!
        }
        is FilteredMealModel -> {
            markColor = item.markCategory?.getColor()!!
            markName = item.markCategory?.getName()!!
        }
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(6.dp))
            .background(color = backgroundColor)
            .padding(horizontal = 0.03.dw, vertical = 0.01.dh)
    ) {
        Image(
            painter = painterResource(id = R.drawable.fill_mark_icon_2),
            contentDescription = "Mark",
            colorFilter = ColorFilter.tint(
                color = markColor
            ),
            modifier = Modifier
                .height(0.023.dh)
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = markName,
            style = TextStyle(
                color = markColor,
                fontWeight = FontWeight.Normal,
                fontSize = 0.016.sh
            ),
        )
    }
}