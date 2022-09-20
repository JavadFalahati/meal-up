package com.jwdfhi.meal_up.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jwdfhi.meal_up.R
import com.jwdfhi.meal_up.ui.theme.Black60Color
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.dw
import com.slaviboy.composeunits.sh

@Composable
fun CustomEmpty(
    showTitle: Boolean = false
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(0.6.dw)
                .align(Alignment.CenterHorizontally)
        ) {
            val painter = painterResource(id = R.drawable.empty_icon_2)
            Image(
                modifier = Modifier
                    .aspectRatio(ratio = painter.intrinsicSize.height / painter.intrinsicSize.width)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                painter = painter,
                contentDescription = "Empty",
                colorFilter = ColorFilter.tint(color = Black60Color),
                contentScale = ContentScale.Fit
            )
        }
        if (showTitle) {
            Box(
                modifier = Modifier
                    .width(width = 0.6.dw)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "No content to show",
                    style = TextStyle(
                        fontSize = 0.020.sh,
                        textAlign = TextAlign.Center,
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        Spacer(modifier = Modifier.height(0.1.dh))
    }
}