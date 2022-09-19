package com.jwdfhi.meal_up.screens.management.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.jwdfhi.meal_up.R
import com.jwdfhi.meal_up.models.ManagementScreenBottomSheetType
import com.jwdfhi.meal_up.screens.management.ManagementViewModel
import com.jwdfhi.meal_up.ui.theme.Black80Color
import com.jwdfhi.meal_up.ui.theme.White100Color
import com.jwdfhi.meal_up.utils.ManagementSettings
import com.jwdfhi.meal_up.utils.setAllManagementPrimaryColor
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.dw
import com.slaviboy.composeunits.sh
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun ManagementScreenBottomSheet(
    viewModel: ManagementViewModel,
    bottomSheetVisibilityState: ModalBottomSheetState,
    managementScreenBottomSheetType: ManagementScreenBottomSheetType,
    openThemeBottomSheet: () -> Unit,
    openPickColorBottomSheet: () -> Unit
) {
    val scope = rememberCoroutineScope()

    val controller = rememberColorPickerController()
    var pickedColor: Color? = null

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .padding(
                    horizontal = 0.07.dw,
                    vertical = 0.03.dh,
                )
        ) {
            when (managementScreenBottomSheetType) {
                ManagementScreenBottomSheetType.Theme -> {
                    ManagementScreenManagementItem(
                        title = stringResource(id = R.string.Primary_color),
                        image = R.drawable.color_dropper_icon_1,
                        color = ManagementSettings.PrimaryColor,
                        onTap = {
                            scope.launch {
                                scope.launch { bottomSheetVisibilityState.hide() }
                                openPickColorBottomSheet()
                            }
                        }
                    )
                }
                ManagementScreenBottomSheetType.About -> {
                    Text(
                        text = stringResource(id = R.string.about_description),
                        style = TextStyle(
                            color = Black80Color,
                            fontSize = 0.023.sh
                        )
                    )
                }
                ManagementScreenBottomSheetType.ColorPicker -> {
                    Column(
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(
                                    horizontal = 0.07.dw,
                                    vertical = 0.03.dh,
                                )
                        ) {
                            HsvColorPicker(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(0.3.dh)
                                    .padding(10.dp),
                                controller = controller,
                                onColorChanged = { colorEnvelope: ColorEnvelope ->
                                    pickedColor = colorEnvelope.color
                                }
                            )
                        }
                    }
                }
            }
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.08.dh)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = ManagementSettings.PrimaryColor
            ),
            onClick = {
                scope.launch {
                    if (managementScreenBottomSheetType == ManagementScreenBottomSheetType.ColorPicker) {
                        if (pickedColor != null) {
                            viewModel.setColorToSharedPreferencesAndManagementSettings(pickedColor!!)
                        }

                        bottomSheetVisibilityState.hide()
                        openThemeBottomSheet()
                        return@launch
                    }

                    bottomSheetVisibilityState.hide()
                }
            },
        ) {
            Text(
                text = when (managementScreenBottomSheetType) {
                    ManagementScreenBottomSheetType.Theme -> stringResource(id = R.string.Done)
                    ManagementScreenBottomSheetType.About -> stringResource(id = R.string.Done)
                    ManagementScreenBottomSheetType.ColorPicker -> stringResource(id = R.string.Save)
                },
                style = TextStyle(
                    color = White100Color,
                    fontSize = 0.022.sh
                ),
                modifier = Modifier
                    .padding(3.dp)
            )
        }
    }

}