package com.jwdfhi.meal_up.screens.meal.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.jwdfhi.meal_up.R
import com.jwdfhi.meal_up.models.MealCategory
import com.jwdfhi.meal_up.models.MealCategoryItemModel
import com.jwdfhi.meal_up.ui.theme.Black80Color
import com.jwdfhi.meal_up.ui.theme.TransparentColor
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.sh

@Composable
fun MealScreenMealCategoryDialog(
    onCategoryTap: (mealCategory: MealCategory) -> Unit,
    onDismissDialog: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismissDialog() },
        title = {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(id = R.string.Select_a_category),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = Black80Color,
                    textAlign = TextAlign.Center,
                    fontSize = 0.029.sh,
                    fontWeight = FontWeight.Bold
                )
            )
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(0.01.dh),
                    text = "",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        color = TransparentColor,
                        fontSize = 0.01.sh,
                    )
                )
                Spacer(modifier = Modifier.height(0.01.dh))
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(0.02.dh)
                ) {
                    items(
                        listOf<MealCategoryItemModel>(
                            MealCategoryItemModel(
                                value = MealCategory.BREAKFAST,
                                backgroundImage = R.drawable.breakfast_background_1,
                                colors = listOf(
                                    Color(0xFF199B15),
                                    Color(0xFF1ECA18),
                                ),
                            ),
                            MealCategoryItemModel(
                                value = MealCategory.LAUNCH,
                                backgroundImage = R.drawable.launch_background_1,
                                colors = listOf(
                                    Color(0xFFC49D27),
                                    Color(0xFFE0C725),
                                ),
                            ),
                            MealCategoryItemModel(
                                value = MealCategory.DINNER,
                                backgroundImage = R.drawable.dinner_background_2,
                                colors = listOf(
                                    Color(0xFFD11A1A),
                                    Color(0xFFFF373E),
                                ),
                            )
                        )
                    ) {
                        MealScreenMealCategoryItem(
                            item = it,
                            onTap = { onCategoryTap(it.value) },
                            height = 0.09.dh
                        )
                    }
                }
            }
        },
        buttons = {},
        properties = DialogProperties(),
        shape = RoundedCornerShape(14.dp)
    )
}