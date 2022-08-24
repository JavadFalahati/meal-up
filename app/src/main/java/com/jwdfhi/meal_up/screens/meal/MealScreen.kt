package com.jwdfhi.meal_up.screens.meal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.jwdfhi.meal_up.R
import com.jwdfhi.meal_up.components.*
import com.jwdfhi.meal_up.models.DataOrExceptionStatus
import com.jwdfhi.meal_up.models.IngredientWithColorModel
import com.jwdfhi.meal_up.models.LoadingType
import com.jwdfhi.meal_up.ui.theme.*
import com.jwdfhi.meal_up.utils.isNullOrEmptyOfServer
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.dw
import com.slaviboy.composeunits.sh

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
                            val mealItem = viewModel.mealDataOrException.collectAsState().value.data!!

                            ConstraintLayout(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                val (backgroundImage, content) = createRefs()

                                Box(
                                    modifier = Modifier
                                        .background(color = GreyBackgroundScreen)
                                        .fillMaxSize()
                                        .constrainAs(backgroundImage) {
                                            top.linkTo(parent.top)
                                            start.linkTo(parent.start)
                                            end.linkTo(parent.end)
                                            width = Dimension.matchParent
                                        }
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Top,
                                    ) {
                                        Image(
                                            painter = rememberAsyncImagePainter(
                                                mealItem.strMealThumb,
                                                contentScale = ContentScale.Crop,
                                            ),
                                            contentDescription = "BackgroundImage",
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                // .fillMaxWidth()
                                                .width(1.dw)
                                                .height(0.43.dh)
                                                .clip(
                                                    shape = RoundedCornerShape(
                                                        bottomStart = 18.dp,
                                                        bottomEnd = 18.dp,
                                                    )
                                                )
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
                                            .padding(horizontal = 0.05.dw, vertical = 0.03.dh)
                                    ) {
                                        // TODO: Split up the composables to components.
                                        // appbar
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .clip(shape = CircleShape)
                                                    .background(color = White50Color.copy(0.6f))
                                                    .clickable {
                                                        viewModel.onBackPressed(navController)
                                                    }
                                            ) {
                                                Image(
                                                    painter = painterResource(id = R.drawable.left_arrow_icon_1),
                                                    contentDescription = "Back",
                                                    colorFilter = ColorFilter.tint(color = White100Color),
                                                    modifier = Modifier
                                                        .height(height = 0.055.dh)
                                                        .padding(8.dp)
                                                )
                                            }
                                            Box(
                                                modifier = Modifier
                                                    .clip(shape = CircleShape)
                                                    .background(color = White100Color)
                                                    .clickable {
                                                        viewModel.onBackPressed(navController)
                                                    }
                                            ) {
                                                Image(
                                                    painter = painterResource(id = R.drawable.un_fill_like_icon_1),
                                                    contentDescription = "Like",
                                                    colorFilter = ColorFilter.tint(color = Black90Color),
                                                    modifier = Modifier
                                                        .height(height = 0.055.dh)
                                                        .padding(8.dp)
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(0.18.dh))
                                        // baseContent
                                        Box(
                                            modifier = Modifier
                                                .height(height = 0.6.dh)
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

                                                    if (!mealItem.strTags.isNullOrEmptyOfServer()) {
                                                        val tagList = mealItem.strTags.split(",")

                                                        LazyRow(
                                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                                        ) {
                                                            items(tagList) { tag ->
                                                                if (tag.trim().isNotEmpty()) {
                                                                    Text(
                                                                        text = "#$tag",
                                                                        color = PrimaryColor,
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
                                                    Spacer(modifier = Modifier.height(0.035.dh))
                                                    Text(
                                                        text = "Instructions",
                                                        color = Black80Color,
                                                        fontWeight = FontWeight.W600,
                                                        fontSize = 0.022.sh
                                                    )
                                                    Spacer(modifier = Modifier.height(0.009.dh))
                                                    Text(
                                                        text =
                                                        if (mealItem.strInstructions.length > 250) mealItem.strInstructions.substring(0, 250) + "..."
                                                        else mealItem.strInstructions,
                                                        color = Black60Color,
                                                        fontWeight = FontWeight.Normal,
                                                        fontSize = 0.022.sh
                                                    )
                                                    Spacer(modifier = Modifier.height(0.02.dh))
                                                    Divider()
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
                                                            IngredientWithColorModel(value = mealItem.strIngredient6, color = Green60Color),
                                                        ).filter { it.value.isNotEmpty() }.shuffled()

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
                                                }
                                            }
                                        }

                                        // mainAction
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(height = 0.13.dh)
                                                .padding(0.007.dw)
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .clip(shape = RoundedCornerShape(10.dp))
                                                    .background(color = White100Color)
                                                    .padding(11.dp)
                                            ) {
                                                when (mealItem.isMarked) {
                                                    false -> Row() {
                                                        Text("sdfsd")
                                                    }
                                                    true -> Row() {
                                                        Text("sdfsd")
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
            }
        }
    }

}