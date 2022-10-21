package com.jwdfhi.meal_up.screens.splash

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.jwdfhi.meal_up.R
import com.jwdfhi.meal_up.components.CustomError
import com.jwdfhi.meal_up.components.CustomLoading
import com.jwdfhi.meal_up.models.DataOrExceptionStatus
import com.jwdfhi.meal_up.models.LoadingType
import com.jwdfhi.meal_up.screens.meal.components.*
import com.jwdfhi.meal_up.ui.theme.GreyBackgroundScreen
import com.jwdfhi.meal_up.ui.theme.TransparentColor
import com.jwdfhi.meal_up.ui.theme.White60Color
import com.jwdfhi.meal_up.ui.theme.White80Color
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.dw

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel
) {

    LaunchedEffect(Unit) { viewModel.initState(navController) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(TransparentColor),
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(GreyBackgroundScreen)
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.splash_background_resized_1),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(width = 0.2.dh, height = 0.2.dh)
                        .clip(shape = RoundedCornerShape(12.dp))
                        .background(color = White80Color.copy(0.4f))
                        // .padding(8.dp)
                ) {
                    Image(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .height(0.2.dh)
                            .clip(shape = RoundedCornerShape(12.dp)),
                        painter = painterResource(id = R.drawable.main_logo_icon),
                        contentDescription = null,
                    )
                }
            }
        }
    }
}