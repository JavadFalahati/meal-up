package com.jwdfhi.meal_up.screens.filter

import android.util.Log
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.jwdfhi.meal_up.components.*
import com.jwdfhi.meal_up.models.*
import com.jwdfhi.meal_up.screens.filter.components.FilterAppbar
import com.jwdfhi.meal_up.screens.filter.components.FilterItem
import com.jwdfhi.meal_up.ui.theme.Black60Color
import com.jwdfhi.meal_up.ui.theme.GreyBackgroundScreen
import com.jwdfhi.meal_up.ui.theme.PrimaryColor
import com.jwdfhi.meal_up.ui.theme.White100Color
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.dw
import com.slaviboy.composeunits.sh
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun FilterScreen(
    navController: NavController,
    viewModel: FilterViewModel
) {

    CustomBackPressHandler(onBackPressed = {})

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(GreyBackgroundScreen),
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(GreyBackgroundScreen)
                .padding(
                    horizontal = 12.dp
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (viewModel.initStateDataOrException.collectAsState().value.status) {
                DataOrExceptionStatus.Loading -> CustomLoading(loadingType = LoadingType.Linear, title = "")
                DataOrExceptionStatus.Failure -> CustomError(
                    title = viewModel.initStateDataOrException.collectAsState().value.exception!!.message!!,
                    tryAgainOnTap = { viewModel.initState() }
                )
                DataOrExceptionStatus.Success -> {
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Spacer(modifier = Modifier.height(height = 0.04.dh))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    // .height(height = 0.12.dh)
                            ) {
                                FilterAppbar(
                                    navController = navController,
                                    onClear = { viewModel.clearScreenState() }
                                )
                            }
                            Spacer(modifier = Modifier.height(height = 0.04.dh))
                            Box(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                            ) {
                                LazyColumn() {
                                    item {
                                        FilterItem<MealCategoryListServiceModel.Category>(
                                            item = FilterItemModel<MealCategoryListServiceModel.Category>(
                                                title = "Categories",
                                                selectItem = { viewModel.selectSingleStateOfFilter<MealCategoryListServiceModel.Category>(it) },
                                                clearItem = { viewModel.clearSingleStateOfFilter<MealCategoryListServiceModel.Category>(it) },
                                                clearAllItems = { viewModel.clearAllStateOfFilter(FilterType.Category) },
                                                items = viewModel.initStateDataOrException.collectAsState().value.data!!.categories
                                            ),
                                            height = 0.2.dh,
                                            verticalMargin = 0.02.dh,
                                            horizontalMargin = 6.dp
                                        )
                                        Spacer(modifier = Modifier.height(0.025.dh))
                                        FilterItem(
                                            item = FilterItemModel<MealIngredientListServiceModel.Meal>(
                                                title = "Ingredients",
                                                selectItem = { viewModel.selectSingleStateOfFilter<MealIngredientListServiceModel.Meal>(it) },
                                                clearItem = { viewModel.clearSingleStateOfFilter<MealIngredientListServiceModel.Meal>(it) },
                                                clearAllItems = { viewModel.clearAllStateOfFilter(FilterType.Ingredient) },
                                                items = viewModel.initStateDataOrException.collectAsState().value.data!!.ingredients
                                            ),
                                            height = 0.2.dh,
                                            verticalMargin = 0.02.dh,
                                            horizontalMargin = 6.dp
                                        )
                                        Spacer(modifier = Modifier.height(0.025.dh))
                                        FilterItem(
                                            item = FilterItemModel<MealAreaListServiceModel.Meal>(
                                                title = "Areas",
                                                selectItem = { viewModel.selectSingleStateOfFilter<MealAreaListServiceModel.Meal>(it) },
                                                clearItem = { viewModel.clearSingleStateOfFilter<MealAreaListServiceModel.Meal>(it) },
                                                clearAllItems = { viewModel.clearAllStateOfFilter(FilterType.Area) },
                                                items = viewModel.initStateDataOrException.collectAsState().value.data!!.areas
                                            ),
                                            height = 0.2.dh,
                                            verticalMargin = 0.02.dh,
                                            horizontalMargin = 6.dp
                                        )
                                    }
                                }
                            }
                        }
                        Column(
                            verticalArrangement = Arrangement.Bottom,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Spacer(modifier = Modifier.height(0.03.dh))
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    // .width(width = 0.7.dw)
                                    .height(height = 0.06.dh)
                                    .align(Alignment.CenterHorizontally)
                                    .clip(shape = RoundedCornerShape(10.dp)),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(0xFF45BE4C)
                                ),
                                onClick = { viewModel.submitFilter() },
                            ) {
                                Text(
                                    text = "Save",
                                    style = TextStyle(
                                        color = White100Color,
                                        fontSize = 0.02.sh
                                    ),
                                    modifier = Modifier
                                        .padding(3.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(0.03.dh))
                        }
                    }
                }
            }
        }
    }
}