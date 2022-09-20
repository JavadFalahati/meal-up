package com.jwdfhi.meal_up.screens.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jwdfhi.meal_up.R
import com.jwdfhi.meal_up.components.*
import com.jwdfhi.meal_up.models.*
import com.jwdfhi.meal_up.screens.filter.components.FilterScreenAppbar
import com.jwdfhi.meal_up.screens.filter.components.FilterItem
import com.jwdfhi.meal_up.ui.theme.GreyBackgroundScreen
import com.jwdfhi.meal_up.ui.theme.White100Color
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.sh

@Composable
fun FilterScreen(
    navController: NavController,
    viewModel: FilterViewModel,
    filterListSelectedItemModel: FilterListSelectedItemModel
) {
    LaunchedEffect(Unit) {
        viewModel.initState(filterListSelectedItemModel = filterListSelectedItemModel)
    }

    CustomBackPressHandler(onBackPressed = { navController.popBackStack() })

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
            when (viewModel.filtersDataOrException.collectAsState().value.status) {
                DataOrExceptionStatus.Loading -> CustomLoading(loadingType = LoadingType.SpoonAndFork, title = "")
                DataOrExceptionStatus.Failure -> CustomError(
                    title = viewModel.filtersDataOrException.collectAsState().value.exception?.message ?: "",
                    tryAgainOnTap = { viewModel.initState(filterListSelectedItemModel = filterListSelectedItemModel) }
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
                                FilterScreenAppbar(
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
                                                title = stringResource(id = R.string.Categories),
                                                selectItem = { viewModel.selectSingleStateOfFilter<MealCategoryListServiceModel.Category>(it) },
                                                clearItem = { viewModel.clearSingleStateOfFilter<MealCategoryListServiceModel.Category>(it) },
                                                clearAllItems = { viewModel.clearAllStateOfFilter(FilterType.Category) },
                                                items = viewModel.filtersDataOrException.collectAsState().value.data?.categories ?: SnapshotStateList()
                                            ),
                                            haveSelectedItem = viewModel.filterListSelectedItemModel.category.isNotEmpty(),
                                            height = 0.2.dh,
                                            verticalMargin = 0.02.dh,
                                            horizontalMargin = 6.dp
                                        )
                                        Spacer(modifier = Modifier.height(0.025.dh))
                                        FilterItem(
                                            item = FilterItemModel<MealIngredientListServiceModel.Meal>(
                                                title = stringResource(id = R.string.Ingredients),
                                                selectItem = { viewModel.selectSingleStateOfFilter<MealIngredientListServiceModel.Meal>(it) },
                                                clearItem = { viewModel.clearSingleStateOfFilter<MealIngredientListServiceModel.Meal>(it) },
                                                clearAllItems = { viewModel.clearAllStateOfFilter(FilterType.Ingredient) },
                                                items = viewModel.filtersDataOrException.collectAsState().value.data?.ingredients ?: SnapshotStateList()
                                            ),
                                            haveSelectedItem = viewModel.filterListSelectedItemModel.ingredients.isNotEmpty(),
                                            height = 0.2.dh,
                                            verticalMargin = 0.02.dh,
                                            horizontalMargin = 6.dp
                                        )
                                        Spacer(modifier = Modifier.height(0.025.dh))
                                        FilterItem(
                                            item = FilterItemModel<MealAreaListServiceModel.Meal>(
                                                title = stringResource(id = R.string.Areas),
                                                selectItem = { viewModel.selectSingleStateOfFilter<MealAreaListServiceModel.Meal>(it) },
                                                clearItem = { viewModel.clearSingleStateOfFilter<MealAreaListServiceModel.Meal>(it) },
                                                clearAllItems = { viewModel.clearAllStateOfFilter(FilterType.Area) },
                                                items = viewModel.filtersDataOrException.collectAsState().value.data?.areas ?: SnapshotStateList()
                                            ),
                                            haveSelectedItem = viewModel.filterListSelectedItemModel.area.isNotEmpty(),
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
                                onClick = { viewModel.submitFilter(navController = navController) },
                            ) {
                                Text(
                                    text = stringResource(id = R.string.Save),
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