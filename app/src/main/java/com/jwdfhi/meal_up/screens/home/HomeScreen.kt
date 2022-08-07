package com.jwdfhi.meal_up.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.jwdfhi.meal_up.components.CustomError
import com.jwdfhi.meal_up.components.CustomLoading
import com.jwdfhi.meal_up.components.CustomTextField
import com.jwdfhi.meal_up.components.MealItem
import com.jwdfhi.meal_up.models.DataOrExceptionStatus
import com.jwdfhi.meal_up.models.LoadingType
import com.jwdfhi.meal_up.ui.theme.LightPrimaryColor
import com.jwdfhi.meal_up.ui.theme.PrimaryColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//@Preview(showBackground = true)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel) {

    val searchState = rememberSaveable() { mutableStateOf("") }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFECECEC)),
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
            Row(
                modifier = Modifier
                    .weight(0.8f)
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .weight(4.0f)
                        .background(Color.Transparent)
                ) {
                    CustomTextField(
                        valueState = searchState,
                        hint = "Searching for a plate?",
                        backgroundColor = LightPrimaryColor,
                        borderRadius = 8.dp
                    )
                }
                Spacer(modifier = Modifier.weight(0.2f))
                Box(
                    modifier = Modifier
                        .weight(0.7f)
                        .clip(shape = RoundedCornerShape(8.dp))
                        .background(PrimaryColor)
                        .fillMaxHeight()
                        .clickable { }
                        .padding(2.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.List,
                        contentDescription = "Filter",
                        tint = Color.White,
                        modifier = Modifier
                            .align(Center)
                    )
                }
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
                        DataOrExceptionStatus.Failure -> CustomError(title = viewModel.randomMealsDataOrException.collectAsState().value.exception!!.message!!)
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