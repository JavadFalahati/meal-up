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
import com.jwdfhi.meal_up.models.FilterListSelectedItemTextModel
import com.jwdfhi.meal_up.models.KeyboardStatusType
import com.jwdfhi.meal_up.models.LoadingType
import com.jwdfhi.meal_up.screens.Screens
import com.jwdfhi.meal_up.screens.home.components.HomeScreenDrawer
import com.jwdfhi.meal_up.screens.home.components.HomeScreenSearchAndFilter
import com.jwdfhi.meal_up.ui.theme.GreyBackgroundScreen
import com.jwdfhi.meal_up.utils.FilterListSelectedItemHelper
import com.slaviboy.composeunits.dh
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

// @Preview(showBackground = true)
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel,
    filterListSelectedItemTextModel: FilterListSelectedItemTextModel
) {

    val scope = rememberCoroutineScope()

    val scaffoldState = rememberScaffoldState()
    val searchState = rememberSaveable() { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val keyboardState by keyboardAsState()

    LaunchedEffect(Unit) {
        viewModel.initState(filterListSelectedItemTextModel = filterListSelectedItemTextModel)
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
                HomeScreenSearchAndFilter(
                    viewModel = viewModel,
                    height = 0.085.dh,
                    keyboardController = keyboardController,
                    keyboardState = keyboardState,
                    focusManager = focusManager,
                    filterIsEnable = FilterListSelectedItemHelper.isNotEmpty(filterListSelectedItemTextModel),
                    searchState = searchState,
                    searchOnTap = { search(viewModel = viewModel, searchState = searchState) },
                    filterOnTap = {
                        navController.navigate(Screens.FilterScreen.name + "/" + Json.encodeToString(filterListSelectedItemTextModel))
                    },
                    disableFilterOnTap = {}
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
                            title = viewModel.mealsDataOrException.collectAsState().value.exception!!.message!!,
                            tryAgainOnTap = { search(viewModel = viewModel, searchState = searchState) }
                        )
                        DataOrExceptionStatus.Success -> {
                            SwipeRefresh(
                                state = rememberSwipeRefreshState(
                                    isRefreshing = viewModel.mealsDataOrException.collectAsState().value.status
                                            == DataOrExceptionStatus.Loading
                                ),
                                onRefresh = { search(viewModel = viewModel, searchState = searchState) }
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
                                                // .weight(9f)
                                                .fillMaxSize(),
                                        ) {
                                            items(viewModel.mealsDataOrException.value.data!!) {
                                                MealItem(
                                                    onTap = { /*TODO: go to meal screen*/ },
                                                    title = it.strMeal,
                                                    imageUrl = it.strMealThumb,
                                                    ingredientList = listOf<String>("it.strIngredient1", "it.strIngredient2"),
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
    searchState: MutableState<String>
) {
    viewModel.viewModelScope.launch(Dispatchers.IO) {
        if (searchState.value.isEmpty()) {
            viewModel.getRandomMeals()
        }
        else if (searchState.value.isNotEmpty()) {
            viewModel.getMealByName(searchState.value)
        }
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