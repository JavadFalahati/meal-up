package com.jwdfhi.meal_up.screens.meal

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.jwdfhi.meal_up.R
import com.jwdfhi.meal_up.components.*
import com.jwdfhi.meal_up.models.DataOrExceptionStatus
import com.jwdfhi.meal_up.models.LoadingType
import com.jwdfhi.meal_up.models.ManagementScreenBottomSheetType
import com.jwdfhi.meal_up.models.MarkModel
import com.jwdfhi.meal_up.screens.Screens
import com.jwdfhi.meal_up.screens.home.components.CustomDrawer
import com.jwdfhi.meal_up.screens.management.components.ManagementScreenBottomSheet
import com.jwdfhi.meal_up.screens.management.components.ManagementScreenManagementItem
import com.jwdfhi.meal_up.screens.management.components.ManagementScreenManagementSection
import com.jwdfhi.meal_up.screens.meal.components.*
import com.jwdfhi.meal_up.ui.theme.*
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.dw
import com.slaviboy.composeunits.sh
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MealScreen(
    navController: NavController,
    viewModel: MealViewModel,
    id: String
) {

    LaunchedEffect(Unit) { viewModel.initState(id = id) }
    CustomBackPressHandler(onBackPressed = { viewModel.onBackPressed(navController) })

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    val mealCategoryDialogIsOpen = remember { mutableStateOf(false) }

    val bottomSheetVisibilityState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(
        sheetState = bottomSheetVisibilityState,
        sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        modifier = Modifier.height(92.dh),
        sheetContent = {
            MealScreenBottomSheet(
                bottomSheetVisibilityState = bottomSheetVisibilityState,
                mealItem = viewModel.mealDataOrException.collectAsState().value.data?.value
            )
        }
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(GreyBackgroundScreen),
            scaffoldState = scaffoldState,
        ) { padding ->

            if (mealCategoryDialogIsOpen.value) {
                MealScreenMealCategoryDialog(
                    onDismissDialog = { mealCategoryDialogIsOpen.value = false },
                    onCategoryTap = { mealCategory ->
                        viewModel.markMeal(mealCategory)

                        mealCategoryDialogIsOpen.value = false
                    }
                )
            }

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
                            DataOrExceptionStatus.Loading -> CustomLoading(loadingType = LoadingType.SpoonAndFork, title = "")
                            DataOrExceptionStatus.Failure -> CustomError(
                                title = viewModel.mealDataOrException.collectAsState().value.exception?.message,
                                tryAgainOnTap = { viewModel.initState(id) }
                            )
                            DataOrExceptionStatus.Success -> {
                                val mealItem = viewModel.mealDataOrException.collectAsState().value.data

                                when (mealItem) {
                                    null -> Box {}
                                    else -> ConstraintLayout(
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
                                                    expandOnTap = {
                                                        scope.launch { bottomSheetVisibilityState.show() }
                                                    },
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
                                                    markOnTap = { mealCategoryDialogIsOpen.value = true },
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
    }

}