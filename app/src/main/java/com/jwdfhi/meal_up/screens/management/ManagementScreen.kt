package com.jwdfhi.meal_up.screens.management

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.jwdfhi.meal_up.R
import com.jwdfhi.meal_up.components.*
import com.jwdfhi.meal_up.models.ManagementScreenBottomSheetType
import com.jwdfhi.meal_up.screens.Screens
import com.jwdfhi.meal_up.screens.home.components.CustomDrawer
import com.jwdfhi.meal_up.screens.management.components.ManagementScreenBottomSheet
import com.jwdfhi.meal_up.screens.management.components.ManagementScreenManagementItem
import com.jwdfhi.meal_up.screens.management.components.ManagementScreenManagementSection
import com.jwdfhi.meal_up.ui.theme.*
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.dw
import com.slaviboy.composeunits.sh
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun ManagementScreen(
    navController: NavController,
    viewModel: ManagementViewModel
) {

    LaunchedEffect(Unit) { viewModel.initState() }

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    CustomBackPressHandler(onBackPressed = { viewModel.onBackPressed(navController) })

    val bottomSheetVisibilityState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val bottomSheetTypeState = remember {
        mutableStateOf(ManagementScreenBottomSheetType.Theme)
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetVisibilityState,
        sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        sheetContent = {
            ManagementScreenBottomSheet(
                viewModel = viewModel,
                bottomSheetVisibilityState = bottomSheetVisibilityState,
                managementScreenBottomSheetType = bottomSheetTypeState.value,
                openThemeBottomSheet = {
                    bottomSheetTypeState.value = ManagementScreenBottomSheetType.Theme
                    scope.launch { bottomSheetVisibilityState.show() }
                },
                openPickColorBottomSheet = {
                    bottomSheetTypeState.value = ManagementScreenBottomSheetType.ColorPicker
                    scope.launch { bottomSheetVisibilityState.show() }
                },
            )
        }
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(GreyBackgroundScreen),
            scaffoldState = scaffoldState,
            drawerContent = {
                CustomDrawer(
                    navController = navController,
                    screenName = Screens.ManagementScreen.name,
                    onCloseDrawer = {
                        scope.launch(Dispatchers.Main) {
                            scaffoldState.drawerState.close()
                        }
                    }
                )
            },
            drawerShape = RoundedCornerShape(
                topEnd = 6.dp,
                bottomEnd = 6.dp
            ),
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(GreyBackgroundScreen)
                    .padding(
                        horizontal = 12.dp
                    ),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(height = 0.03.dh))
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = stringResource(id = R.string.Menu),
                        modifier = Modifier
                            .height(height = 0.04.dh)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                scope.launch(Dispatchers.Main) {
                                    scaffoldState.drawerState.open()
                                }
                            }
                    )
                    Spacer(modifier = Modifier.width(width = 0.05.dw))
                    Text(
                        text = stringResource(id = R.string.Management),
                        style = TextStyle(
                            color = Black90Color,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.W600,
                            fontSize = 0.032.sh
                        )
                    )
                }
                Spacer(modifier = Modifier.height(height = 0.05.dh))
                LazyColumn(
                    modifier = Modifier
                ) {
                    items(
                        count = 1
                    ) {
                        ManagementScreenManagementSection(
                            title = stringResource(id = R.string.Account),
                        ) {
                            ManagementScreenManagementItem(
                                title = stringResource(id = R.string.Profile),
                                image = R.drawable.person_icon_1,
                                color = Black60Color,
                                onTap = {
                                    Toast.makeText(viewModel.context, R.string.Account_section_not_implement_yet, Toast.LENGTH_LONG).show()
                                }
                            )
                        }
                        Spacer(modifier = Modifier.height(height = 0.02.dh))
                        ManagementScreenManagementSection(
                            title = stringResource(id = R.string.Settings)
                        ) {
                            ManagementScreenManagementItem(
                                title = stringResource(id = R.string.Theme),
                                image = R.drawable.theme_icon_1,
                                color = Pink70Color,
                                onTap = {
                                    scope.launch(Dispatchers.Main) {
                                        bottomSheetTypeState.value = ManagementScreenBottomSheetType.Theme
                                        bottomSheetVisibilityState.show()
                                    }

                                }
                            )
                            ManagementScreenManagementItem(
                                title = stringResource(id = R.string.About),
                                image = R.drawable.info_icon_1,
                                color = DarkBlue70Color,
                                onTap = {
                                    scope.launch(Dispatchers.Main) {
                                        bottomSheetTypeState.value = ManagementScreenBottomSheetType.About
                                        bottomSheetVisibilityState.show()
                                    }
                                }
                            )
                        }
                    }

                }
            }
        }
    }

}