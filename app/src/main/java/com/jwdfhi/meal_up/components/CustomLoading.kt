package com.jwdfhi.meal_up.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.jwdfhi.meal_up.R
import com.jwdfhi.meal_up.models.LoadingType
import com.jwdfhi.meal_up.utils.ManagementSettings
import com.slaviboy.composeunits.sh

@Composable
fun CustomLoading(
    loadingType: LoadingType = LoadingType.Circle,
    title: String = "",
    color: Color = ManagementSettings.PrimaryColor,
) {
    val spoonAndForkComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.spoon_and_fork_lottie_gif_1))
    val spoonAndForkProgress by animateLottieCompositionAsState(spoonAndForkComposition)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxHeight()
    ) {
        when (loadingType) {
            LoadingType.Circle -> {
                CircularProgressIndicator(
                    color = color
                )
            }
            LoadingType.Linear -> {
                LinearProgressIndicator(
                    color = color
                )
            }
            LoadingType.SpoonAndFork -> LottieAnimation(
                composition = spoonAndForkComposition,
                progress = { spoonAndForkProgress }
            )
        }
        if (title.isNotEmpty()) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 0.020.sh
                ),
            )
        }
    }
}