package com.jwdfhi.meal_up.screens.meal

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.jwdfhi.meal_up.components.*
import com.jwdfhi.meal_up.models.DataOrExceptionStatus
import com.jwdfhi.meal_up.models.LoadingType
import com.jwdfhi.meal_up.models.MarkModel
import com.jwdfhi.meal_up.screens.meal.components.MealScreenActions
import com.jwdfhi.meal_up.screens.meal.components.MealScreenAppbar
import com.jwdfhi.meal_up.screens.meal.components.MealScreenBackground
import com.jwdfhi.meal_up.screens.meal.components.MealScreenContent
import com.jwdfhi.meal_up.ui.theme.*
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.dw

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
                                ) { MealScreenBackground(mealItem = mealItem.value) }

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
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(TransparentColor)
                                            .padding(
                                                start = 0.05.dw,
                                                end = 0.05.dw,
                                                top = 0.03.dh,
                                                bottom = 0.015.dh
                                            ),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.SpaceBetween,
                                    ) {
                                        MealScreenAppbar(
                                            mealItem = mealItem.value,
                                            likeOnTap = { viewModel.likeOrUnLikeMeal() },
                                            backOnTap = { viewModel.onBackPressed(navController) }
                                        )
                                        Spacer(modifier = Modifier.height(0.17.dh))
                                        MealScreenContent(
                                            mealItem = mealItem.value,
                                            expandOnTap = { /*TODO: navigate to mealDetailScreen*/ },
                                            sourceOnTap = {
                                              viewModel.context.startActivity(
                                                  Intent(Intent.ACTION_VIEW, Uri.parse(mealItem.value.strSource))
                                              )
                                            },
                                            youtubeOnTap = {
                                                viewModel.context.startActivity(
                                                    Intent(Intent.ACTION_VIEW, Uri.parse(mealItem.value.strYoutube))
                                                )
                                            }
                                        )
                                        Spacer(modifier = Modifier.height(0.01.dh))
                                        MealScreenActions(
                                            mealItem = mealItem.value,
                                            markOnTap = { viewModel.markMeal(MarkModel(name = "test", color = Red80Color.value.toInt())) },
                                            removeMarkOnTap = { viewModel.unMarkMeal() },
                                            goToMarksOnTap = {}
                                        )
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