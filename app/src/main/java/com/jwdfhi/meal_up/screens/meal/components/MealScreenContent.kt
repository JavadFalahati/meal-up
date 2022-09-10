package com.jwdfhi.meal_up.screens.meal.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.jwdfhi.meal_up.R
import com.jwdfhi.meal_up.models.IngredientWithColorModel
import com.jwdfhi.meal_up.models.MealModel
import com.jwdfhi.meal_up.ui.theme.*
import com.jwdfhi.meal_up.utils.ManagementSettings
import com.jwdfhi.meal_up.utils.isNullOrEmptyOfServer
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.dw
import com.slaviboy.composeunits.sh

@Composable
fun MealScreenContent(
    mealItem: MealModel,
    expandOnTap: () -> Unit,
    sourceOnTap: () -> Unit,
    youtubeOnTap: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(0.007.dw)
    ) {
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = White100Color)
                .padding(11.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top
                    ) {
                        if (!mealItem.strTags.isNullOrEmptyOfServer()) {
                            val tagList = mealItem.strTags?.split(",")

                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                items(tagList!!) { tag ->
                                    if (tag.trim().isNotEmpty()) {
                                        Text(
                                            text = "#$tag",
                                            color = ManagementSettings.PrimaryColor,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 0.019.sh
                                        )
                                    }

                                }
                            }
                            Spacer(modifier = Modifier.height(0.015.dh))
                        }
                        Text(
                            text = mealItem.strMeal,
                            color = Black80Color,
                            fontWeight = FontWeight.Bold,
                            fontSize = 0.034.sh
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(width = 0.04.dh, height = 0.04.dh)
                            .clip(shape = CircleShape)
                            .background(color = Black20Color.copy(0.2f), shape = CircleShape)
                            .clickable { expandOnTap() }
                            .fillMaxHeight()
                            .padding(6.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .clip(shape = CircleShape)
                                .background(TransparentColor, shape = CircleShape),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.expand_icon_1),
                                contentDescription = "Expand",
                                colorFilter = ColorFilter.tint(color = Black60Color.copy(0.6f)),
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(0.028.dh))
                Text(
                    text = "Instructions",
                    color = Black80Color,
                    fontWeight = FontWeight.W600,
                    fontSize = 0.022.sh
                )
                Spacer(modifier = Modifier.height(0.009.dh))
                Text(
                    modifier = Modifier
                        .heightIn(0.0.dp, 0.15.dh)
                        .verticalScroll(rememberScrollState(0)),
                    text =
                    if (mealItem.strInstructions.length > 250)
                        (mealItem.strInstructions.substring(0, 250) + "...").replace("\\r\\n\\r\\n", "\\r\\n")
                    else
                        mealItem.strInstructions,
                    color = Black60Color,
                    fontWeight = FontWeight.Normal,
                    fontSize = 0.022.sh
                )
                Spacer(modifier = Modifier.height(0.02.dh))
                Text(
                    text = "Ingredients",
                    color = Black80Color,
                    fontWeight = FontWeight.W600,
                    fontSize = 0.022.sh
                )
                Spacer(modifier = Modifier.height(0.009.dh))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    val firstXIngredientShuffledList = listOf<IngredientWithColorModel>(
                        IngredientWithColorModel(value = mealItem.strIngredient1, color = Red60Color),
                        IngredientWithColorModel(value = mealItem.strIngredient2, color = Blue60Color),
                        IngredientWithColorModel(value = mealItem.strIngredient3, color = Yellow60Color),
                        IngredientWithColorModel(value = mealItem.strIngredient4, color = Orange60Color),
                        IngredientWithColorModel(value = mealItem.strIngredient5, color = Cyan60Color),
                    ).filter { !(it.value.isNullOrEmpty()) }

                    items(
                        items = firstXIngredientShuffledList
                    ) { ingredient ->
                        Box(
                            modifier = Modifier
                        ) {
                            Text(
                                text = ingredient.value
                                        +
                                        if (firstXIngredientShuffledList.last() == ingredient) "..."
                                        else ",",
                                textAlign = TextAlign.Start,
                                color = Black60Color,
                                fontWeight = FontWeight.Normal,
                                fontSize = 0.020.sh,
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(0.02.dh))
                Divider()
                Spacer(modifier = Modifier.height(0.02.dh))
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MealScreenContentAction(
                        title = "Source",
                        backgroundColor = Blue90Color,
                        image = painterResource(id = R.drawable.link_icon_3),
                        onTap = { sourceOnTap() }
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    MealScreenContentAction(
                        title = "Youtube",
                        backgroundColor = Red80Color,
                        image = painterResource(id = R.drawable.fill_youtube_icon_1),
                        onTap = { youtubeOnTap() }
                    )
                }
            }
        }
    }
}