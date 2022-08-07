package com.jwdfhi.meal_up.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jwdfhi.meal_up.models.LoadingType
import com.jwdfhi.meal_up.ui.theme.PrimaryColor

@Composable
fun CustomLoading(
    loadingType: LoadingType = LoadingType.Circle,
    title: String = "",
    color: Color = PrimaryColor,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
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
        }
        if (title.isNotEmpty()) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title
            )
        }
    }
}