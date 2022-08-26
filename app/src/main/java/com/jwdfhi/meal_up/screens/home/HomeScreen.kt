package com.jwdfhi.meal_up.screens.home

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.jwdfhi.meal_up.components.*
import com.jwdfhi.meal_up.models.DataOrExceptionStatus
import com.jwdfhi.meal_up.models.FilterListSelectedItemModel
import com.jwdfhi.meal_up.models.KeyboardStatusType
import com.jwdfhi.meal_up.models.LoadingType
import com.jwdfhi.meal_up.screens.Screens
import com.jwdfhi.meal_up.screens.home.components.CustomDrawer
import com.jwdfhi.meal_up.screens.home.components.CustomSearchAndFilter
import com.jwdfhi.meal_up.ui.theme.GreyBackgroundScreen
import com.jwdfhi.meal_up.utils.Constant
import com.jwdfhi.meal_up.utils.isNotEmpty
import com.slaviboy.composeunits.dh
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel,
    filterListSelectedItemModelShouldNotUse: FilterListSelectedItemModel,
) {

    val filterIsNotEmptyState = remember {
        mutableStateOf(viewModel.filterListSelectedItemModel.isNotEmpty())
    }

    val scope = rememberCoroutineScope()

    val scaffoldState = rememberScaffoldState()
    val searchState = rememberSaveable() { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val keyboardState by keyboardAsState()

    LaunchedEffect(Unit) {
        viewModel.initState(
            filterListSelectedItemModel = filterListSelectedItemModelShouldNotUse,
            refresh = true,
            searchValue = searchState.value
        )

        filterIsNotEmptyState.value = viewModel.filterListSelectedItemModel.isNotEmpty()
    }

    CustomBackPressHandler(onBackPressed = {
        onBackPressed(
            context = viewModel.context,
            viewModel = viewModel,
            keyboardController = keyboardController,
            keyboardStatusType = keyboardState
        )
    })

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(GreyBackgroundScreen),
        scaffoldState = scaffoldState,
        drawerContent = {
            CustomDrawer(
                navController = navController,
                screenName = Screens.HomeScreen.name,
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
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                modifier = Modifier
                    .align(Alignment.Start)
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
            Spacer(modifier = Modifier.height(height = 0.03.dh))
            Box(modifier = Modifier.height(height = 0.085.dh)) {
                val meals = viewModel.mealsDataOrException.collectAsState().value.data
                val mealsLoading: Boolean = (viewModel.mealsDataOrException.collectAsState().value.status == DataOrExceptionStatus.Loading)

                CustomSearchAndFilter(
                    height = 0.085.dh,
                    keyboardController = keyboardController,
                    keyboardState = keyboardState,
                    focusManager = focusManager,
                    filterIsEnable = filterIsNotEmptyState.value,
                    searchState = searchState,
                    searchOnTap = {
                        search(
                            viewModel = viewModel,
                            searchState = searchState,
                        )
                    },
                    clearSearchOnTap = {

                        if (searchState.value.trim().isNotEmpty()) {
                            searchState.value = ""
                            if ((meals == null || meals.isEmpty()) || (filterIsNotEmptyState.value)) {
                                search(viewModel, searchState)
                            }
                            return@CustomSearchAndFilter
                        }

                        if (meals == null || meals.isEmpty()) {
                            search(viewModel, searchState)
                        }

                        keyboardController?.hide()
                        focusManager.clearFocus()
                    },
                    filterOnTap = {
                        if (mealsLoading) { return@CustomSearchAndFilter }

                        navController.navigate(
                            route = Screens.FilterScreen.name + "/" + Json.encodeToString(viewModel.filterListSelectedItemModel)
                        )
                    },
                    clearFilterOnTap = {
                        if (mealsLoading) { return@CustomSearchAndFilter }

                        filterIsNotEmptyState.value = false
                        viewModel.filterListSelectedItemModel = FilterListSelectedItemModel()
                        navController.currentBackStackEntry!!.savedStateHandle
                            .set<String>(Constant.FILTERS_ARGUMENT_KEY, Json.encodeToString(FilterListSelectedItemModel()))

                        search(viewModel, searchState)
                    }
                )
            }
            Spacer(modifier = Modifier.height(height = 0.02.dh))
            Box(
                modifier = Modifier
                    .align(CenterHorizontally)
            ) {
                Box(modifier = Modifier.align(Center)) {
                    when (viewModel.mealsDataOrException.collectAsState().value.status) {
                        DataOrExceptionStatus.Loading -> CustomLoading(loadingType = LoadingType.Linear, title = "")
                        DataOrExceptionStatus.Failure -> CustomError(
                            title = "Error accrued while connecting to server",
                            tryAgainOnTap = { search(viewModel, searchState) }
                        )
                        DataOrExceptionStatus.Success -> {
                            SwipeRefresh(
                                state = rememberSwipeRefreshState(
                                    isRefreshing = viewModel.mealsDataOrException.collectAsState().value.status
                                            == DataOrExceptionStatus.Loading
                                ),
                                onRefresh = { search(viewModel, searchState) }
                            ) {
                                CompositionLocalProvider(
                                    LocalOverscrollConfiguration provides null
                                ) {
                                    if (viewModel.mealsDataOrException.collectAsState().value.data?.isEmpty() == true) {
                                        CustomEmpty()
                                    }
                                    else {
                                        LazyColumn(
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
                                                    likeOnTap = { viewModel.likeOrUnLikeMeal(item) },
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

private fun search(
    viewModel: HomeViewModel,
    searchState: MutableState<String>,
) {
    viewModel.viewModelScope.launch(Dispatchers.IO) {
        viewModel.searchMealByName(
            filterListSelectedItemModel = viewModel.filterListSelectedItemModel,
            name = searchState.value
        )
    }
}

@ExperimentalComposeUiApi
private fun onBackPressed(
    context: Context,
    viewModel: HomeViewModel,
    keyboardStatusType: KeyboardStatusType,
    keyboardController: SoftwareKeyboardController?
) {
    // TODO: Debug this.
    Log.d("onBackPressed", "onBackPressed: 0")
    when (keyboardStatusType) {
        KeyboardStatusType.Opened -> keyboardController?.hide()
        KeyboardStatusType.Closed -> {
            if (viewModel.onBackPressedTimerIsFinished) {
                Log.d("onBackPressed", "onBackPressed: 1")
                viewModel.startOnBackPressedTimer()
                Log.d("onBackPressed", "onBackPressed: 2")
                Toast.makeText(context, "Press again to exit.", Toast.LENGTH_LONG).show()
                Log.d("onBackPressed", "onBackPressed: 3")
            }
            else { (context as? Activity)?.finish() }
        }
    }
}