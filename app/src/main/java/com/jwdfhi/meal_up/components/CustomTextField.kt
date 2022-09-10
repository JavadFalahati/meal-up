package com.jwdfhi.meal_up.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jwdfhi.meal_up.utils.ManagementSettings

@Composable
fun CustomTextField(
    valueState: MutableState<String>,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    padding: Dp = 0.dp,
    backgroundColor: Color,
    borderRadius: Dp = 8.dp,
    hint: String = "",
    maxLength: Int = 200,
    isError: Boolean = false,
    errorText: (MutableState<String>) -> String = { "" },
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    keyboardActions: KeyboardActions = KeyboardActions(),
    leadingIcon: @Composable () -> Unit = {},
    trailingIcon: @Composable () -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = valueState.value,
            onValueChange = { if (valueState.value.length < maxLength) { valueState.value = it } },
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(borderRadius)),
            enabled = enabled,
            readOnly = readOnly,
            singleLine = true,
            isError = isError,
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                backgroundColor = backgroundColor,
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = ManagementSettings.PrimaryColor
            ),
            placeholder = {
                Text(
                    text = hint,
                    color = ManagementSettings.PrimaryColor
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction,
            ),
            keyboardActions = keyboardActions,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon
        )
        // if (isError) {
        //     Spacer(modifier = Modifier.height(4.dp))
        //     Text(
        //         text = errorText(valueState),
        //         style = TextStyle(
        //             color = Color.Red
        //         )
        //     )
        // }
    }

}