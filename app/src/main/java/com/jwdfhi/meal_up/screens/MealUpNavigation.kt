package com.jwdfhi.meal_up.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jwdfhi.meal_up.screens.filter.FilterScreen
import com.jwdfhi.meal_up.screens.filter.FilterViewModel
import com.jwdfhi.meal_up.screens.home.HomeScreen
import com.jwdfhi.meal_up.screens.home.HomeViewModel
import com.jwdfhi.meal_up.screens.introduction.IntroductionScreen
import com.jwdfhi.meal_up.screens.like.LikeScreen
import com.jwdfhi.meal_up.screens.management.ManagementScreen
import com.jwdfhi.meal_up.screens.mark.MarkScreen
import com.jwdfhi.meal_up.screens.splash.SplashScreen
import com.jwdfhi.meal_up.ui.theme.*


@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun MealUpNavigation() {
    val navController = rememberNavController()
    val systemUiController = rememberSystemUiController()

    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.name,
        builder = {

            composable(
                route = Screens.HomeScreen.name
            ) {
                val homeViewModel = hiltViewModel<HomeViewModel>()

                systemUiController.setStatusBarColor(
                    color = Primary80Color
                )
                HomeScreen(
                     navController = navController,
                     viewModel = homeViewModel
                )
            }

            composable(
                route = Screens.FilterScreen.name
            ) {
                val filterViewModel = hiltViewModel<FilterViewModel>()

                systemUiController.setStatusBarColor(
                    color = White80Color
                )
                FilterScreen(
                    navController = navController,
                    viewModel = filterViewModel
                )
            }

            composable(
                route = Screens.IntroductionScreen.name
            ) {
                IntroductionScreen(
                    navController = navController,
                )
            }

            composable(
                route = Screens.LikeScreen.name
            ) {
                LikeScreen(
                    navController = navController,
                )
            }

            composable(
                route = Screens.ManagementScreen.name
            ) {
                ManagementScreen(
                    navController = navController,
                )
            }

            composable(
                route = Screens.MarkScreen.name
            ) {
                MarkScreen(
                    navController = navController,
                )
            }

            composable(
                route = Screens.SplashScreen.name
            ) {
                SplashScreen(
                    navController = navController,
                )
            }

        }
    )

}