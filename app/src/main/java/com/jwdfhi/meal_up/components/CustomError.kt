package com.jwdfhi.meal_up.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.jwdfhi.meal_up.R
import com.jwdfhi.meal_up.ui.theme.PrimaryColor
import com.slaviboy.composeunits.dw
import com.slaviboy.composeunits.sh

@Composable
fun CustomError(
    title: String?,
    color: Color = PrimaryColor,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.error_1),
            contentDescription = "Error",
            modifier = Modifier
                .width(5.dw)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title ?: "Un expected error accrued",
            style = TextStyle(
                fontSize = 0.025.sh
            ),
        )
    }
}