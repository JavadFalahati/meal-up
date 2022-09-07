package com.jwdfhi.meal_up.screens.management.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jwdfhi.meal_up.ui.theme.Black80Color
import com.jwdfhi.meal_up.ui.theme.Black90Color
import com.jwdfhi.meal_up.ui.theme.White100Color
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.dw
import com.slaviboy.composeunits.sh

@Composable
fun ManagementScreenManagementSection(
    title: String,
    horizontalPadding: Dp = 0.03.dw,
    verticalPadding: Dp = 0.022.dh,
    content: @Composable () -> Unit,
) {
    Column(
       modifier = Modifier
           .fillMaxWidth()
           .clip(shape = RoundedCornerShape(10.dp))
           .background(White100Color)
           .padding(
               horizontal = horizontalPadding,
               vertical = verticalPadding
           ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = title,
            style = TextStyle(
                color = Black90Color,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.W600,
                fontSize = 0.027.sh
            )
        )
        Spacer(modifier = Modifier.height(0.02.dh))
        content()
    }
}