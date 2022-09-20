package com.jwdfhi.meal_up.screens.meal.components

import android.content.res.Resources
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jwdfhi.meal_up.R
import com.jwdfhi.meal_up.components.customVerticalScrollbar
import com.jwdfhi.meal_up.models.IngredientWithColorModel
import com.jwdfhi.meal_up.models.MealModel
import com.jwdfhi.meal_up.models.MealScreenBottomSheetItemSectionModel
import com.jwdfhi.meal_up.ui.theme.*
import com.jwdfhi.meal_up.utils.ManagementSettings
import com.jwdfhi.meal_up.utils.ifIsEmptyReplaceItWith
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.dw
import com.slaviboy.composeunits.sh
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MealScreenBottomSheet(
    mealItem: MealModel?,
    bottomSheetVisibilityState: ModalBottomSheetState,
) {
    val scope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()

    when (mealItem) {
        null -> Box(modifier = Modifier.height(1.dp)) {}
        else -> Column(
            modifier = Modifier
                .height(0.92.dh)
                .background(GreyBackgroundScreen),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start,
        ) {
            Column(
                modifier = Modifier
                    .height(0.82.dh)
                    .padding(
                        horizontal = 0.04.dw,
                        vertical = 0.03.dh,
                    )
            ) {
                Text(
                    text = mealItem.strMeal,
                    style = TextStyle(
                        color = Black80Color,
                        fontWeight = FontWeight.Bold,
                        fontSize = 0.034.sh
                    )
                )
                Spacer(modifier = Modifier.height(0.03.dh))
                CompositionLocalProvider(
                    LocalOverscrollConfiguration provides null
                ) {
                    LazyColumn(
                        /*modifier = Modifier.customVerticalScrollbar(state = lazyListState),*/
                        verticalArrangement = Arrangement.spacedBy(0.02.dh),
                    ) {
                        items(
                            listOf<MealScreenBottomSheetItemSectionModel>(
                                MealScreenBottomSheetItemSectionModel(
                                    title = R.string.Ingredients,
                                    icon = R.drawable.ingredient_icon_1,
                                    content = {
                                        LazyRow(
                                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                                            modifier = Modifier
                                        ) {
                                            val ingredientShuffledList = listOf<IngredientWithColorModel>(
                                                IngredientWithColorModel(value = mealItem.strIngredient1, color = Red60Color),
                                                IngredientWithColorModel(value = mealItem.strIngredient2, color = Blue60Color),
                                                IngredientWithColorModel(value = mealItem.strIngredient3, color = Yellow60Color),
                                                IngredientWithColorModel(value = mealItem.strIngredient4, color = Orange60Color),
                                                IngredientWithColorModel(value = mealItem.strIngredient5, color = Cyan60Color),
                                                IngredientWithColorModel(value = mealItem.strIngredient6, color = Red60Color),
                                                IngredientWithColorModel(value = mealItem.strIngredient7, color = Blue60Color),
                                                IngredientWithColorModel(value = mealItem.strIngredient8, color = Yellow60Color),
                                                IngredientWithColorModel(value = mealItem.strIngredient9, color = Orange60Color),
                                                IngredientWithColorModel(value = mealItem.strIngredient10, color = Cyan60Color),
                                                IngredientWithColorModel(value = mealItem.strIngredient11, color = Red60Color),
                                                IngredientWithColorModel(value = mealItem.strIngredient12, color = Blue60Color),
                                                IngredientWithColorModel(value = mealItem.strIngredient13, color = Yellow60Color),
                                                IngredientWithColorModel(value = mealItem.strIngredient14, color = Orange60Color),
                                                IngredientWithColorModel(value = mealItem.strIngredient15, color = Cyan60Color),
                                                IngredientWithColorModel(value = mealItem.strIngredient16, color = Red60Color),
                                                IngredientWithColorModel(value = mealItem.strIngredient17, color = Blue60Color),
                                                IngredientWithColorModel(value = mealItem.strIngredient18, color = Yellow60Color),
                                                IngredientWithColorModel(value = mealItem.strIngredient19, color = Orange60Color),
                                                IngredientWithColorModel(value = mealItem.strIngredient20, color = Cyan60Color),
                                            ).filter { !(it.value.isNullOrEmpty()) }

                                            items(
                                                items = ingredientShuffledList
                                            ) { ingredient ->
                                                Box(
                                                    modifier = Modifier
                                                ) {
                                                    Text(
                                                        text = ingredient.value
                                                                +
                                                                if (ingredientShuffledList.last() == ingredient) ""
                                                                else ",",
                                                        style = TextStyle(
                                                            textAlign = TextAlign.Start,
                                                            color = Black60Color,
                                                            fontWeight = FontWeight.Normal,
                                                            fontSize = 0.020.sh,
                                                        )
                                                    )
                                                }
                                            }
                                        }
                                    }
                                ),
                                MealScreenBottomSheetItemSectionModel(
                                    title = R.string.Instructions,
                                    icon = R.drawable.food_recipe_icon_1,
                                    content = {
                                        Text(
                                            text = mealItem.strInstructions,
                                            style = TextStyle(
                                                textAlign = TextAlign.Start,
                                                color = Black60Color,
                                                fontWeight = FontWeight.Normal,
                                                fontSize = 0.020.sh,
                                            )
                                        )
                                    }
                                ),
                                MealScreenBottomSheetItemSectionModel(
                                    title = R.string.Category_and_Area,
                                    icon = R.drawable.food_recipe_icon_1,
                                    content = {},
                                    splitted = true,
                                    firstSplittedTitle = R.string.Category,
                                    firstSplittedIcon = R.drawable.category_icon_1,
                                    firstSplittedContent = {
                                        Text(
                                            text = mealItem.strCategory.ifIsEmptyReplaceItWith(stringResource(id = R.string.Unknown)),
                                            style = TextStyle(
                                                textAlign = TextAlign.Start,
                                                color = Black60Color,
                                                fontWeight = FontWeight.Normal,
                                                fontSize = 0.020.sh,
                                            )
                                        )
                                    },
                                    secondSplittedTitle = R.string.Area,
                                    secondSplittedIcon = R.drawable.globe_icon_1,
                                    secondSplittedContent = {
                                        Text(
                                            text = mealItem.strArea.ifIsEmptyReplaceItWith(stringResource(id = R.string.Unknown)),
                                            style = TextStyle(
                                                textAlign = TextAlign.Start,
                                                color = Black60Color,
                                                fontWeight = FontWeight.Normal,
                                                fontSize = 0.020.sh,
                                            )
                                        )
                                    }
                                ),
                            )
                        ) { MealScreenBottomSheetItemSection(item = it) }
                    }
                }
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 0.01.dh)
                    .height(0.07.dh)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = ManagementSettings.PrimaryColor
                ),
                onClick = { scope.launch { bottomSheetVisibilityState.hide() } },
            ) {
                Text(
                    text = stringResource(id = R.string.OK),
                    style = TextStyle(
                        color = White100Color,
                        fontSize = 0.022.sh
                    ),
                    modifier = Modifier
                        .padding(3.dp)
                )
            }
        }
    }

}