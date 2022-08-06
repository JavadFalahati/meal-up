package com.jwdfhi.meal_up.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
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
import com.jwdfhi.meal_up.components.CustomTextField
import com.jwdfhi.meal_up.components.MealItem
import com.jwdfhi.meal_up.ui.theme.LightPrimaryColor
import com.jwdfhi.meal_up.ui.theme.PrimaryColor

@Preview(showBackground = true)
@Composable
fun HomeScreen(/*navController: NavController, viewModel: HomeViewModel*/) {

    val searchState = rememberSaveable() { mutableStateOf("") }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE2E2E2)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE2E2E2))
                .padding(
                    horizontal = 12.dp
                ),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.weight(0.3f))
            Row(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .weight(6.3f)
                        .background(Color.Transparent)
                ) {
                    CustomTextField(
                        valueState = searchState,
                        hint = "Searching for a plate?",
                        backgroundColor = LightPrimaryColor,
                        borderRadius = 8.dp
                    )
                }
                Spacer(modifier = Modifier.weight(0.3f))
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(shape = RoundedCornerShape(8.dp))
                        .background(PrimaryColor)
                        .fillMaxHeight()
                        .padding(2.dp)
                        .clickable { }
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

            LazyColumn(
                modifier = Modifier
                    .weight(9f)
                    .fillMaxSize()
            ) {
                items(listOf<String>("text", "test")) {
                    Box(
                        modifier = Modifier
                            .weight(4f)
                    ) {
                        MealItem(
                            onTap = { /*TODO*/ },
                            title = it,
                            imageUrl = "https://www.themealdb.com/images/media/meals/9f4z6v1598734293.jpg",
                            ingredientList = listOf<String>("text", "test"),
                        )
                    }
                }
            }
        }
    }
}