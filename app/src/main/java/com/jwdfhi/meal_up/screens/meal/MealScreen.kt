package com.jwdfhi.meal_up.screens.meal

import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.jwdfhi.meal_up.R
import com.jwdfhi.meal_up.components.*
import com.jwdfhi.meal_up.models.DataOrExceptionStatus
import com.jwdfhi.meal_up.models.FilterListSelectedItemModel
import com.jwdfhi.meal_up.models.LoadingType
import com.jwdfhi.meal_up.screens.Screens
import com.jwdfhi.meal_up.screens.home.components.HomeScreenDrawer
import com.jwdfhi.meal_up.screens.home.components.HomeScreenSearchAndFilter
import com.jwdfhi.meal_up.ui.theme.*
import com.jwdfhi.meal_up.utils.Constant
import com.jwdfhi.meal_up.utils.FilterListSelectedItemHelper
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.dw
import com.slaviboy.composeunits.sh
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun MealScreen(
    navController: NavController,
    viewModel: MealViewModel,
    id: String
) {

    LaunchedEffect(Unit) { viewModel.initState(id = id) }

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    CustomBackPressHandler(onBackPressed = { viewModel.onBackPressed(navController) })

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(GreyBackgroundScreen),
        scaffoldState = scaffoldState,
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFECECEC)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                Box(modifier = Modifier.align(Alignment.Center)) {
                    when (viewModel.mealDataOrException.collectAsState().value.status) {
                        DataOrExceptionStatus.Loading -> CustomLoading(loadingType = LoadingType.Linear, title = "")
                        DataOrExceptionStatus.Failure -> CustomError(
                            title = viewModel.mealDataOrException.collectAsState().value.exception!!.message!!,
                            tryAgainOnTap = { viewModel.initState(id) }
                        )
                        DataOrExceptionStatus.Success -> {
                            ConstraintLayout(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                val (backgroundImage, content) = createRefs()

                                Box(
                                    modifier = Modifier
                                        .clip(shape = RoundedCornerShape(8.dp))
                                        .background(color = GreyBackgroundScreen)
                                        .fillMaxSize()
                                        .constrainAs(backgroundImage) {
                                            top.linkTo(parent.top)
                                            start.linkTo(parent.start)
                                            end.linkTo(parent.end)
                                            width = Dimension.fillToConstraints
                                        }
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Top
                                    ) {
                                        Image(
                                            painter = rememberAsyncImagePainter(
                                                viewModel.mealDataOrException.collectAsState().value.data!!.strMealThumb
                                            ),
                                            contentDescription = "BackgroundImage",
                                            modifier = Modifier
                                                .height(0.43.dh)
                                                .fillMaxWidth()
                                                .clip(shape = RoundedCornerShape(10.dp))
                                        )
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .constrainAs(content) {
                                            top.linkTo(backgroundImage.top)
                                            start.linkTo(backgroundImage.start)
                                            bottom.linkTo(backgroundImage.bottom)
                                            end.linkTo(backgroundImage.end)
                                        }
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Top,
                                        modifier = Modifier
                                            .background(TransparentColor)
                                            .padding(10.dp),
                                    ) {
                                        // TODO: Split up the composables to components.
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .clip(shape = RoundedCornerShape(6.dp))
                                                    .clickable {
                                                        viewModel.onBackPressed(
                                                            navController
                                                        )
                                                    }
                                            ) {
                                                Image(
                                                    painter = painterResource(id = R.drawable.back_left_icon_2),
                                                    contentDescription = "Back",
                                                    colorFilter = ColorFilter.tint(color = Black90Color),
                                                    modifier = Modifier
                                                        .height(height = 0.055.dh)
                                                        .padding(8.dp)
                                                )
                                            }
                                            Box(
                                                modifier = Modifier
                                                    .size(width = 0.055.dh, height = 0.055.dh)
                                                    .clip(shape = CircleShape)
                                                    .background(Red100Color, shape = CircleShape)
                                                    .clickable { clearFilterOnTap() }
                                                    // TODO: Refactor the rest.
                                                    .fillMaxHeight()
                                                    .padding(10.dp)
                                            ) {
                                                Column(
                                                    modifier = Modifier
                                                        .align(Alignment.Center)
                                                        .clip(shape = CircleShape)
                                                        .background(
                                                            TransparentColor,
                                                            shape = CircleShape
                                                        ),
                                                    horizontalAlignment = Alignment.CenterHorizontally,
                                                    verticalArrangement = Arrangement.Center
                                                ) {
                                                    Image(
                                                        painter = painterResource(id = R.drawable.close_icon_2),
                                                        contentDescription = "Clear",
                                                        colorFilter = ColorFilter.tint(color = White100Color),
                                                        modifier = Modifier
                                                            .align(Alignment.CenterHorizontally)
                                                    )
                                                }
                                            }
                                            Spacer(modifier = Modifier.width(0.05.dw))
                                            Text(
                                                text = "Filters",
                                                style = TextStyle(
                                                    color = Black90Color,
                                                    textAlign = TextAlign.Start,
                                                    fontWeight = FontWeight.W600,
                                                    fontSize = 0.034.sh
                                                )
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(0.020.dh))

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