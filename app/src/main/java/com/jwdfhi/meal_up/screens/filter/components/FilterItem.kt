package com.jwdfhi.meal_up.screens.filter.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jwdfhi.meal_up.R
import com.jwdfhi.meal_up.models.*
import com.jwdfhi.meal_up.ui.theme.*
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.dw
import com.slaviboy.composeunits.sh

@Composable
fun <T> FilterItem(
    item: FilterItemModel<T>,
    height: Dp? = null,
    verticalMargin: Dp,
    horizontalMargin: Dp,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(White100Color)
            .padding(horizontal = horizontalMargin, vertical = verticalMargin),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(6.dp))
                    .background(Color.Transparent)
                    .padding(
                        horizontal = 8.dp,
                        vertical = 4.dp,
                    ),
            ) {
                Text(
                    text = item.title,
                    style = TextStyle(
                        color = PrimaryColor,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                        fontSize = 0.02.sh
                    )
                )
            }
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(6.dp))
                    .clickable { item.clearAllItems() }
                    .padding(
                        horizontal = 8.dp,
                        vertical = 4.dp,
                    ),
            ) {
                Text(
                    text = "Clear",
                    style = TextStyle(
                        color = Black50Color,
                        textAlign = TextAlign.End,
                        fontWeight = FontWeight.Normal,
                        fontSize = 0.015.sh
                    )
                )
            }
        }
        Spacer(modifier = Modifier.height(0.035.dh))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(0.017.dw)
        ) {

            items(item.items) { filterSubItem ->

                val filterType: FilterType = when (filterSubItem) {
                    is MealCategoryListServiceModel.Category -> FilterType.Category
                    is MealIngredientListServiceModel.Meal -> FilterType.Ingredient
                    is MealAreaListServiceModel.Meal -> FilterType.Area
                    else -> FilterType.Category
                }

                val filterTitle: String = when (filterType) {
                    FilterType.Category -> (filterSubItem as MealCategoryListServiceModel.Category).strCategory
                    FilterType.Ingredient -> (filterSubItem as MealIngredientListServiceModel.Meal).strIngredient
                    FilterType.Area -> (filterSubItem as MealAreaListServiceModel.Meal).strArea
                }

                val filterIsSelected: Boolean = when (filterType) {
                    FilterType.Category -> (filterSubItem as MealCategoryListServiceModel.Category).isSelected
                    FilterType.Ingredient -> (filterSubItem as MealIngredientListServiceModel.Meal).isSelected
                    FilterType.Area -> (filterSubItem as MealAreaListServiceModel.Meal).isSelected
                }

                Row(
                    modifier = Modifier
                        .height(height = 0.04.dh)
                        .clip(shape = RoundedCornerShape(4.dp))
                        .background(
                            color = if (filterIsSelected) PrimaryColor else Black50Color
                        )
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = if (filterIsSelected) null else LocalIndication.current
                        ) {
                            item.selectItem(filterSubItem as T)
                        }
                        .padding(
                            horizontal = 5.dp,
                            vertical = 3.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    if (filterIsSelected) {
                        Image(
                            painter = painterResource(id = R.drawable.close_icon_2),
                            contentDescription = "Clear",
                            colorFilter = ColorFilter.tint(color = Black80Color),
                            modifier = Modifier
                                .padding(4.dp)
                                .clickable { item.clearItem(filterSubItem as T) }
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                    }
                    Text(
                        text = filterTitle,
                        style = TextStyle(
                            color = if (filterIsSelected) { White100Color } else { Black80Color },
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Normal,
                            fontSize = 0.018.sh
                        )
                    )
                }
            }
        }
    }

}