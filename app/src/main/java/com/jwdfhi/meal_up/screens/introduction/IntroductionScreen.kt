package com.jwdfhi.meal_up.screens.introduction

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.jwdfhi.meal_up.R
import com.jwdfhi.meal_up.ui.theme.*
import com.jwdfhi.meal_up.utils.ManagementSettings
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.dw
import com.slaviboy.composeunits.sh
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun IntroductionScreen(
    navController: NavController,
    viewModel: IntroductionViewModel
) {
    LaunchedEffect(Unit) { viewModel.initState() }

    val scope = rememberCoroutineScope()
    val horizontalPageViewState = rememberPagerState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(TransparentColor),
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFECECEC))
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                count = viewModel.introductionItems().size,
                state = horizontalPageViewState
            ) { itemIndex ->
                val item = viewModel.introductionItems()[itemIndex]

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    Image(
                        painter = painterResource(id = item.backgroundImage),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .height(height = 0.38.dh)
                            .clip(shape = RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp))
                            .background(color = White100Color)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(vertical = 0.02.dh, horizontal = 0.07.dw)
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Top,
                            ) {
                                Text(
                                    modifier = Modifier.width(0.7.dw),
                                    text = item.title,
                                    style = TextStyle(
                                        textAlign = TextAlign.Center,
                                        color = Black90Color,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 0.028.sh
                                    )
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    modifier = Modifier.width(0.7.dw),
                                    text = item.description,
                                    style = TextStyle(
                                        textAlign = TextAlign.Center,
                                        color = Black60Color,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 0.022.sh
                                    )
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .width(0.85.dw),
                                verticalAlignment = Alignment.Bottom,
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                val isLastItem: Boolean = (itemIndex + 1) == viewModel.introductionItems().size

                                when (isLastItem) {
                                    false -> {
                                        Box(
                                            modifier = Modifier
                                                .align(Alignment.Bottom)
                                                .clip(
                                                    shape = RoundedCornerShape(
                                                        10.dp
                                                    )
                                                )
                                                .background(TransparentColor)
                                                .clickable { viewModel.saveStatusInSharedPreferencesAndNavigateToHomeScreen(navController) }
                                        ) {
                                            Text(
                                                text = "Skip now",
                                            )
                                        }
                                        Box(
                                            modifier = Modifier
                                                .clip(shape = CircleShape)
                                                .background(color = item.color)
                                                .clickable {
                                                    viewModel.navigateToNextHorizontalPage(
                                                        navController = navController,
                                                        scope = scope,
                                                        horizontalPageViewState = horizontalPageViewState
                                                    )
                                                }
                                                .padding(12.dp)
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .clip(shape = CircleShape)
                                                    .background(color = White100Color)
                                            ) {
                                                Image(
                                                    painter = painterResource(
                                                        id = R.drawable.back_right_icon_1
                                                    ),
                                                    contentDescription = "Like",
                                                    colorFilter = ColorFilter.tint(
                                                        color = item.color
                                                    ),
                                                    modifier = Modifier
                                                        .height(height = 0.04.dh)
                                                        .padding(6.dp)
                                                )
                                            }
                                        }

                                    }
                                    true -> {
                                        Button(
                                            modifier = Modifier
                                                .align(Alignment.Bottom)
                                                .fillMaxWidth()
                                                .clip(
                                                    shape = RoundedCornerShape(
                                                        10.dp
                                                    )
                                                ),
                                            colors = ButtonDefaults.buttonColors(
                                                backgroundColor = item.color
                                            ),
                                            onClick = {
                                                viewModel.navigateToNextHorizontalPage(
                                                    navController = navController,
                                                    scope = scope,
                                                    horizontalPageViewState = horizontalPageViewState
                                                )
                                            },
                                        ) {
                                            Text(
                                                text = "Get started",
                                                style = TextStyle(
                                                    color = White100Color,
                                                    fontSize = 0.02.sh
                                                ),
                                                modifier = Modifier
                                                    .padding(6.dp)
                                            )
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
            }

        }
    }
}