package com.jwdfhi.meal_up.screens.introduction

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.jwdfhi.meal_up.R
import com.jwdfhi.meal_up.ui.theme.*
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.dw
import com.slaviboy.composeunits.sh

@ExperimentalPagerApi
@Composable
fun IntroductionScreen(
    navController: NavController,
    viewModel: IntroductionViewModel
) {
    val currenHorizontalPagetIndex = remember {
        mutableStateOf(0)
    }

    val scope = rememberCoroutineScope()
    val horizontalPageViewState = rememberPagerState()

    LaunchedEffect(Unit) {
        currenHorizontalPagetIndex.value = 0

        viewModel.initState()
    }

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
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                HorizontalPager(
                    count = viewModel.introductionItems.size,
                    state = horizontalPageViewState
                ) { itemIndex ->
                    val item = viewModel.introductionItems[itemIndex]
                    currenHorizontalPagetIndex.value = horizontalPageViewState.currentPage

                    Box(
                        modifier = Modifier
                            .height(0.7.dh)
                            .fillMaxWidth()
                    ) {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth(),
                            painter = painterResource(id = item.backgroundImage),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(height = 0.38.dh)
                        .clip(shape = RoundedCornerShape(topEnd = 14.dp, topStart = 14.dp))
                        .background(color = White100Color)
                ) {
                    val item = viewModel.introductionItems.get(currenHorizontalPagetIndex.value)

                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(vertical = 0.03.dh, horizontal = 0.07.dw),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Top,
                        ) {
                            HorizontalPagerIndicator(
                                pagerState = horizontalPageViewState,
                                spacing = 8.dp,
                                activeColor = item.color,
                                inactiveColor = item.color.copy(0.5f),
                                indicatorHeight = 0.007.dh,
                                indicatorWidth = 0.1.dw,
                                indicatorShape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                            )
                            Spacer(modifier = Modifier.height(0.031.dh))
                            Text(
                                modifier = Modifier.width(0.7.dw),
                                text = stringResource(id = item.title),
                                style = TextStyle(
                                    textAlign = TextAlign.Center,
                                    color = Black90Color,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 0.029.sh
                                )
                            )
                            Spacer(modifier = Modifier.height(0.031.dh))
                            Text(
                                modifier = Modifier.width(0.7.dw),
                                text = stringResource(id = item.description),
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
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            val isLastItem: Boolean = (currenHorizontalPagetIndex.value + 1) == viewModel.introductionItems.size

                            when (isLastItem) {
                                false -> {
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.CenterVertically)
                                            .clip(
                                                shape = RoundedCornerShape(
                                                    10.dp
                                                )
                                            )
                                            .background(TransparentColor)
                                            .clickable {
                                                viewModel.saveStatusInSharedPreferencesAndNavigateToHomeScreen(
                                                    navController
                                                )
                                            }
                                    ) {
                                        Text(
                                            modifier = Modifier
                                                .padding(horizontal = 8.dp, vertical = 4.dp),
                                            text = stringResource(id = R.string.Skip_now),
                                            style = TextStyle(
                                                color = Black60Color,
                                                fontSize = 0.018.sh
                                            )
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
                                                contentDescription = stringResource(id = R.string.Next),
                                                colorFilter = ColorFilter.tint(
                                                    color = item.color
                                                ),
                                                modifier = Modifier
                                                    .height(height = 0.046.dh)
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
                                            text = stringResource(id = R.string.Get_started),
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