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
import com.jwdfhi.meal_up.ui.theme.White100Color
import com.jwdfhi.meal_up.utils.ManagementSettings
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.dw
import com.slaviboy.composeunits.sh
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun ManagementScreenBottomSheetPickColor(
    viewModel: ManagementViewModel,
    bottomSheetVisibilityState: ModalBottomSheetState,
) {
    val scope = rememberCoroutineScope()

    val controller = rememberColorPickerController()
    var pickedColor: Color? = null

//    if (bottomSheetVisibilityState.isVisible) {
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
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = ManagementSettings.PrimaryColor
                ),
                onClick = {
                    scope.launch {
                        if (pickedColor != null) {
                            ManagementSettings.PrimaryColor = pickedColor!!
                            // TODO: Save color to sharedPreference
                        }

                        bottomSheetVisibilityState.hide()
                    }
                },
            ) {
                Text(
                    text = stringResource(id = R.string.Done),
                    style = TextStyle(
                        color = White100Color,
                        fontSize = 0.02.sh
                    ),
                    modifier = Modifier
                        .padding(3.dp)
                )
            }
        }
//    }
}