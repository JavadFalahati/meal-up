package com.jwdfhi.meal_up.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.jwdfhi.meal_up.R
import com.jwdfhi.meal_up.components.CustomTextField
import com.jwdfhi.meal_up.models.KeyboardStatusType
import com.jwdfhi.meal_up.screens.home.HomeViewModel
import com.jwdfhi.meal_up.ui.theme.*
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.dw

@ExperimentalComposeUiApi
@Composable
fun HomeScreenSearchAndFilter(
    viewModel: HomeViewModel,
    height: Dp,
    keyboardState: KeyboardStatusType,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager,
    searchState: MutableState<String>,
    searchOnTap: (value: String) -> Unit,
    clearSearchOnTap: () -> Unit,
    filterIsEnable: Boolean,
    filterOnTap: () -> Unit,
    clearFilterOnTap: () -> Unit
) {
    val filterWidth = 0.23.dw

    Row(
        modifier = Modifier
             .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .width(width = 0.72.dw)
                .background(Color.Transparent)
        ) {
            CustomTextField(
                valueState = searchState,
                hint = "Searching for a plate?",
                backgroundColor = LightPrimaryColor,
                borderRadius = 8.dp,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search,
                keyboardActions = KeyboardActions(
                    onSearch = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                        searchOnTap(searchState.value)
                    }
                ),
                maxLength = 30,
                // isError = searchState.value.trim().isEmpty(),
                errorText = { valueState ->
                    if (valueState.value.trim().isEmpty()) {
                        return@CustomTextField "Search input can't be empty"
                    }
                    else return@CustomTextField ""
                },
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
                                .clickable {
                                    keyboardController?.hide()
                                    focusManager.clearFocus()
                                    searchOnTap(searchState.value)
                                }
                        )
                        Spacer(modifier = Modifier.weight(0.2f))
                    }
                },
                trailingIcon = {
                    if (searchState.value.trim().isNotEmpty() || keyboardState == KeyboardStatusType.Opened) {
                        Column(
                            modifier = Modifier.align(Center),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Spacer(modifier = Modifier.weight(0.2f))
                            Image(
                                painter = painterResource(id = R.drawable.close_icon_2),
                                contentDescription = "Close",
                                colorFilter = ColorFilter.tint(color = Black60Color),
                                modifier = Modifier
                                    .weight(0.3f)
                                    .align(CenterHorizontally)
                                    .padding(4.dp)
                                    .clip(shape = RoundedCornerShape(50.dp))
                                    .clickable { clearSearchOnTap() }
                            )
                            Spacer(modifier = Modifier.weight(0.2f))
                        }
                    }
                },
            )
        }
        Spacer(modifier = Modifier.width(width = 0.02.dw))
        Box(
            modifier = Modifier
                 .width(width = filterWidth)
                .padding(horizontal = 8.dp)
        ) {
            ConstraintLayout(
                modifier = Modifier
                     .width(width = filterWidth)
            ) {
                val (filterIcon, closeIcon) = createRefs()

                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(8.dp))
                        .background(color = if (!filterIsEnable) PrimaryColor else Primary80Color)
                        .fillMaxHeight()
                        .clickable { filterOnTap() }
                        .padding(2.dp)
                        .constrainAs(filterIcon) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
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
                if (filterIsEnable) {
                    Box(
                        modifier = Modifier
                            .constrainAs(closeIcon) {
                                bottom.linkTo(filterIcon.top)
                                end.linkTo(filterIcon.start)
                                top.linkTo(filterIcon.top)
                                start.linkTo(filterIcon.start)
                            }
                            .padding(
                                top = 12.dp,
                                start = 12.dp,
                                end = 6.dp,
                                bottom = 6.dp,
                            )
                    ) {
                        Box(
                            modifier = Modifier
                                .size(width = 0.045.dh, height = 0.045.dh)
                                .clip(shape = CircleShape)
                                .background(Red80Color, shape = CircleShape)
                                .clickable { clearFilterOnTap() }
                                .fillMaxHeight()
                                .padding(10.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .align(Center)
                                    .clip(shape = CircleShape)
                                    .background(TransparentColor, shape = CircleShape),
                                horizontalAlignment = CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.close_icon_2),
                                    contentDescription = "Clear",
                                    colorFilter = ColorFilter.tint(color = White100Color),
                                    modifier = Modifier
                                        .align(CenterHorizontally)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}