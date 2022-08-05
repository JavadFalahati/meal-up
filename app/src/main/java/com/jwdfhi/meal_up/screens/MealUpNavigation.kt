package com.jwdfhi.meal_up.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jwdfhi.meal_up.screens.filter.FilterScreen
import com.jwdfhi.meal_up.screens.home.HomeScreen
import com.jwdfhi.meal_up.screens.home.HomeViewModel
import com.jwdfhi.meal_up.screens.introduction.IntroductionScreen
import com.jwdfhi.meal_up.screens.like.LikeScreen
import com.jwdfhi.meal_up.screens.management.ManagementScreen
import com.jwdfhi.meal_up.screens.mark.MarkScreen
import com.jwdfhi.meal_up.screens.splash.SplashScreen


@Composable
fun MealUpNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.name,
        builder = {

            composable(
                route = Screens.HomeScreen.name
            ) {
                val homeViewModel = hiltViewModel<HomeViewModel>()

                HomeScreen(
                    navController = navController,
                    viewModel = homeViewModel
                )
            }

            composable(
                route = Screens.FilterScreen.name
            ) {
                FilterScreen(
                    navController = navController,
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