package com.jwdfhi.meal_up.screens.meal.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jwdfhi.meal_up.models.MealModel
import com.jwdfhi.meal_up.models.MealScreenBottomSheetItemSectionModel
import com.jwdfhi.meal_up.ui.theme.Black80Color
import com.jwdfhi.meal_up.ui.theme.GreyBackgroundScreen
import com.jwdfhi.meal_up.ui.theme.White100Color
import com.jwdfhi.meal_up.utils.ManagementSettings
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.dw
import com.slaviboy.composeunits.sh
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun MealScreenBottomSheet(
    mealItem: MealModel?,
    bottomSheetVisibilityState: ModalBottomSheetState,
) {
    val scope = rememberCoroutineScope()

    when (mealItem) {
        null -> Box {}
        else -> Column(
            modifier = Modifier
                .background(GreyBackgroundScreen),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start,
        ) {
            Box(
                modifier = Modifier
                    .padding(
                        horizontal = 0.07.dw,
                        vertical = 0.03.dh,
                    )
            ) {
                Text(
                    text = mealItem.strMeal,
                    color = Black80Color,
                    fontWeight = FontWeight.Bold,
                    fontSize = 0.034.sh
                )
                Spacer(modifier = Modifier.height(0.03.dh))
                LazyColumn() {
                    items(
                        listOf<MealScreenBottomSheetItemSectionModel>(
                            // TODO: Complete filling this.
                            MealScreenBottomSheetItemSectionModel(
                                title = "Ingredients",
                                content = {

                                }
                            ),
                            MealScreenBottomSheetItemSectionModel(
                                title = "Instructions",
                                content = {

                                }
                            ),
                            MealScreenBottomSheetItemSectionModel(
                                title = "Category and Area",
                                content = {},
                                splitted = true,
                                firstSplittedTitle = "Category",
                                firstSplittedContent = {

                                },
                                secondSplittedTitle = "Area",
                                secondSplittedContent = {

                                }
                            ),
                        )
                    ) { MealScreenBottomSheetItemSection(item = it) }
                }
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 0.02.dh)
                    .height(0.08.dh)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = ManagementSettings.PrimaryColor
                ),
                onClick = { scope.launch { bottomSheetVisibilityState.hide() } },
            ) {
                Text(
                    text = "Done",
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