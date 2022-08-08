package com.jwdfhi.meal_up.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jwdfhi.meal_up.R
import com.jwdfhi.meal_up.components.CustomTextField
import com.jwdfhi.meal_up.ui.theme.LightPrimaryColor
import com.jwdfhi.meal_up.ui.theme.PrimaryColor
import com.jwdfhi.meal_up.ui.theme.White100Color
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.dw
import com.slaviboy.composeunits.sh

@Composable
fun HomeScreenSearchAndFilter(searchState: MutableState<String>) {
    Row(
        modifier = Modifier
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
                borderRadius = 8.dp,
                leadingIcon = {
                    Column(
                        modifier = Modifier.align(Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Spacer(modifier = Modifier.weight(0.2f))
                        Image(
                            painter = painterResource(id = R.drawable.search_icon_1),
                            contentDescription = "Search",
                            colorFilter = ColorFilter.tint(color = PrimaryColor),
                            modifier = Modifier
                                .weight(0.5f)
                                .align(CenterHorizontally)
                                .padding(4.dp)
                                .clip(shape = RoundedCornerShape(50.dp))
                                .clickable { }
                        )
                        Spacer(modifier = Modifier.weight(0.2f))
                    }
                }
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
            Column(
                modifier = Modifier.align(Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.weight(0.2f))
                Image(
                    painter = painterResource(id = R.drawable.filter_icon_1),
                    contentDescription = "Filter",
                    colorFilter = ColorFilter.tint(color = White100Color),
                    modifier = Modifier
                        .weight(0.5f)
                        .align(CenterHorizontally)
                )
                Spacer(modifier = Modifier.weight(0.2f))
            }
        }
    }
}