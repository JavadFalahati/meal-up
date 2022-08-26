package com.jwdfhi.meal_up.screens.like

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.jwdfhi.meal_up.components.*
import com.jwdfhi.meal_up.models.DataOrExceptionStatus
import com.jwdfhi.meal_up.models.LoadingType
import com.jwdfhi.meal_up.screens.Screens
import com.jwdfhi.meal_up.screens.home.components.CustomDrawer
import com.jwdfhi.meal_up.screens.home.components.CustomSearchAndFilter
import com.jwdfhi.meal_up.ui.theme.Black90Color
import com.jwdfhi.meal_up.ui.theme.GreyBackgroundScreen
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.sh
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@Composable
fun LikeScreen(
    navController: NavController,
    viewModel: LikeViewModel
) {

    val scope = rememberCoroutineScope()

    val scaffoldState = rememberScaffoldState()
    val searchState = rememberSaveable() { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val keyboardState by keyboardAsState()

    LaunchedEffect(Unit) {
        viewModel.initState()
    }

    CustomBackPressHandler(onBackPressed = { viewModel.onBackPressed(navController) })

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(GreyBackgroundScreen),
        scaffoldState = scaffoldState,
        drawerContent = {
            CustomDrawer(
                navController = navController,
                screenName = Screens.LikeScreen.name,
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
            Spacer(modifier = Modifier.height(height = 0.03.dh))
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    modifier = Modifier
                        .height(height = 0.04.dh)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            scope.launch(Dispatchers.Main) {
                                scaffoldState.drawerState.open()
                            }
                        }
                )
                Spacer(modifier = Modifier.width(width = 7.dp))
                Text(
                    text = "Liked Meals",
                    style = TextStyle(
                        color = Black90Color,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.W600,
                        fontSize = 0.034.sh
                    )
                )
            }
            Spacer(modifier = Modifier.height(height = 0.03.dh))
            Box(modifier = Modifier.height(height = 0.085.dh)) {
                val meals = viewModel.mealsDataOrException.collectAsState().value.data
                val mealsLoading: Boolean = (viewModel.mealsDataOrException.collectAsState().value.status == DataOrExceptionStatus.Loading)

                CustomSearchAndFilter(
                    height = 0.085.dh,
                    keyboardController = keyboardController,
                    keyboardState = keyboardState,
                    focusManager = focusManager,
                    filterVisibility = false,
                    filterIsEnable = false,
                    searchState = searchState,
                    searchOnTap = {
                        viewModel.searchMealByName(searchState.value)
                    },
                    clearSearchOnTap = {

                        if (searchState.value.trim().isNotEmpty()) {
                            searchState.value = ""
                            if (meals == null || meals.isEmpty()) {
                                viewModel.searchMealByName(searchState.value)
                            }
                            return@CustomSearchAndFilter
                        }

                        if (meals == null || meals.isEmpty()) {
                            viewModel.searchMealByName(searchState.value)
                        }

                        keyboardController?.hide()
                        focusManager.clearFocus()
                    },
                    filterOnTap = {},
                    clearFilterOnTap = {}
                )
            }
            Spacer(modifier = Modifier.height(height = 0.02.dh))
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                Box(modifier = Modifier.align(Alignment.Center)) {
                    when (viewModel.mealsDataOrException.collectAsState().value.status) {
                        DataOrExceptionStatus.Loading -> CustomLoading(loadingType = LoadingType.Linear, title = "")
                        DataOrExceptionStatus.Failure -> CustomError(
                            title = "Error accrued while connecting to server",
                            tryAgainOnTap = { viewModel.searchMealByName(searchState.value) }
                        )
                        DataOrExceptionStatus.Success -> {
                            SwipeRefresh(
                                state = rememberSwipeRefreshState(
                                    isRefreshing = viewModel.mealsDataOrException.collectAsState().value.status
                                            == DataOrExceptionStatus.Loading
                                ),
                                onRefresh = { viewModel.searchMealByName(searchState.value) }
                            ) {
                                CompositionLocalProvider(
                                    LocalOverscrollConfiguration provides null
                                ) {
                                    when (viewModel.mealsDataOrException.collectAsState().value.data?.isEmpty()!!) {
                                        true -> CustomEmpty()
                                        false -> LazyColumn(
                                            modifier = Modifier
                                                .fillMaxSize(),
                                        ) {
                                            items(viewModel.mealsDataOrException.value.data!!) { item ->
                                                MealItem(
                                                    onTap = {
                                                        navController.navigate(
                                                            route = Screens.MealScreen.name
                                                                    + "/" +
                                                                    item.idMeal
                                                        )
                                                    },
                                                    likeOnTap = {},
                                                    item = item,
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
}