package com.jwdfhi.meal_up.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jwdfhi.meal_up.R
import com.jwdfhi.meal_up.ui.theme.PrimaryColor
import com.jwdfhi.meal_up.ui.theme.White100Color
import com.slaviboy.composeunits.dw
import com.slaviboy.composeunits.sh

@Composable
fun CustomError(
    title: String?,
    color: Color = PrimaryColor,
    tryAgain: Boolean = true,
    tryAgainOnTap: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .width(5.dw)
        ) {
            val painter = painterResource(id = R.drawable.error_1)
            Image(
                modifier = Modifier
                    .aspectRatio(ratio = painter.intrinsicSize.height / painter.intrinsicSize.width)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                painter = painter,
                contentDescription = "Error",
                contentScale = ContentScale.Fit
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .width(width = 0.7.dw)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = title ?: "Un expected error accrued",
                style = TextStyle(
                    fontSize = 0.020.sh,
                    textAlign = TextAlign.Center,
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            modifier = Modifier
                .width(width = 0.7.dw)
                .align(Alignment.CenterHorizontally)
                .clip(shape = RoundedCornerShape(4.dp)),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = color
            ),
            onClick = { tryAgainOnTap() },
        ) {
            Text(
                text = "Try again",
                style = TextStyle(
                    color = White100Color,
                    fontSize = 0.02.sh
                ),
                modifier = Modifier
                    .padding(3.dp)
            )
        }
    }
}