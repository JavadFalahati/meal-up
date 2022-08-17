package com.jwdfhi.meal_up.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jwdfhi.meal_up.models.FilterListSelectedItemTextModel
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
import com.jwdfhi.meal_up.utils.Constant
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


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
                systemUiController.setStatusBarColor(
                    color = Primary80Color
                )

                val homeViewModel = hiltViewModel<HomeViewModel>()

                var filterListSelectedItemTextModel: FilterListSelectedItemTextModel = FilterListSelectedItemTextModel()
                if (navController.currentBackStackEntry!!.savedStateHandle.contains(Constant.FILTERS_ARGUMENT_KEY)) {
                    val filterListSelectedItemTextModelNullable = navController.currentBackStackEntry!!.savedStateHandle
                        .get<String>(Constant.FILTERS_ARGUMENT_KEY)

                    if (filterListSelectedItemTextModelNullable != null) {
                        filterListSelectedItemTextModel =
                            Json.decodeFromString<FilterListSelectedItemTextModel>(filterListSelectedItemTextModelNullable)
                    }
                }

                HomeScreen(
                     navController = navController,
                     viewModel = homeViewModel,
                     filterListSelectedItemTextModel = filterListSelectedItemTextModel
                )
            }

            composable(
                route = Screens.FilterScreen.name + "/{${Constant.FILTERS_ARGUMENT_KEY}}",
                arguments = listOf(
                    navArgument(
                        name = Constant.FILTERS_ARGUMENT_KEY,
                        builder = {
                            type = NavType.StringType
                        }
                    )
                )
            ) { navBackStackEntry ->
                systemUiController.setStatusBarColor(
                    color = White80Color
                )

                val filterViewModel = hiltViewModel<FilterViewModel>()

                var filterListSelectedItemTextModel = FilterListSelectedItemTextModel()

                navBackStackEntry.arguments?.getString(Constant.FILTERS_ARGUMENT_KEY).let {
                    if (it != null) {
                        filterListSelectedItemTextModel = Json.decodeFromString<FilterListSelectedItemTextModel>(it)
                    }

                    FilterScreen(
                        navController = navController,
                        viewModel = filterViewModel,
                        filterListSelectedItemTextModel = filterListSelectedItemTextModel
                    )
                }

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