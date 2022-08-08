package com.jwdfhi.meal_up.screens.home

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.jwdfhi.meal_up.components.CustomError
import com.jwdfhi.meal_up.components.CustomLoading
import com.jwdfhi.meal_up.components.MealItem
import com.jwdfhi.meal_up.models.DataOrExceptionStatus
import com.jwdfhi.meal_up.models.LoadingType
import com.jwdfhi.meal_up.screens.home.components.HomeScreenDrawer
import com.jwdfhi.meal_up.screens.home.components.HomeScreenSearchAndFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// @Preview(showBackground = true)
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel) {

    val scope = rememberCoroutineScope()

    val scaffoldState = rememberScaffoldState()
    val searchState = rememberSaveable() { mutableStateOf("") }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFECECEC)),
        scaffoldState = scaffoldState,
        drawerContent = {
            HomeScreenDrawer(
                navController = navController,
                onCloseDrawer = {
                    scope.launch(Dispatchers.Main) {
                        scaffoldState.drawerState.close()
                    }
                }
            )
        },
        drawerShape = RoundedCornerShape(
            topEnd = 6.dp,
            bottomEnd = 6.dp
        )
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFECECEC))
                .padding(
                    horizontal = 12.dp
                ),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.weight(0.3f))
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                modifier = Modifier
                    .align(Alignment.Start)
                    .weight(0.45f)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        scope.launch(Dispatchers.Main) {
                            scaffoldState.drawerState.open()
                        }
                    }
            )
            Spacer(modifier = Modifier.weight(0.3f))
            Box(modifier = Modifier.weight(1.0f)) {
                HomeScreenSearchAndFilter(
                    searchState = searchState,
                    onSearch = {
                        viewModel.viewModelScope.launch(Dispatchers.IO) { viewModel.getMealByQuery(it) }
                    }
                )
            }
            Spacer(modifier = Modifier.weight(0.1f))
            Box(
                modifier = Modifier
                    .weight(9f)
                    .align(CenterHorizontally)
            ) {
                Box(modifier = Modifier.align(Center)) {
                    when (viewModel.randomMealsDataOrException.collectAsState().value.status) {
                        DataOrExceptionStatus.Loading -> CustomLoading(loadingType = LoadingType.Linear, title = "Please wait...")
                        DataOrExceptionStatus.Failure -> CustomError(
                            title = viewModel.randomMealsDataOrException.collectAsState().value.exception!!.message!!,
                            tryAgainOnTap = {
                                viewModel.viewModelScope.launch(Dispatchers.IO) { viewModel.getRandomMeals() }
                            }
                        )
                        DataOrExceptionStatus.Success -> {
                            SwipeRefresh(
                                state = rememberSwipeRefreshState(
                                    isRefreshing = viewModel.randomMealsDataOrException.collectAsState().value.status
                                            == DataOrExceptionStatus.Loading
                                ),
                                onRefresh = {
                                    viewModel.viewModelScope.launch(Dispatchers.IO) {
                                        viewModel.getRandomMeals()
                                    }
                                }
                            ) {
                                CompositionLocalProvider(
                                    LocalOverscrollConfiguration provides null
                                ) {
                                    LazyColumn(
                                        modifier = Modifier
                                            // .weight(9f)
                                            .fillMaxSize(),
                                    ) {
                                        items(viewModel.randomMealsDataOrException.value.data!!) {
                                            MealItem(
                                                onTap = { /*TODO*/ },
                                                title = it.strMeal,
                                                imageUrl = it.strMealThumb,
                                                ingredientList = listOf<String>(it.strIngredient1, it.strIngredient2),
                                                margin = 8.dp,
                                                padding = 8.dp
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