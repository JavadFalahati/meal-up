package com.jwdfhi.meal_up.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jwdfhi.meal_up.ui.theme.PrimaryColor

@Composable
fun CustomTextField(
    valueState: MutableState<String>,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    padding: Dp = 0.dp,
    backgroundColor: Color,
    borderRadius: Dp = 8.dp,
    hint: String = "",
    haveSearchIcon: Boolean = true,
    searchOnTap: () -> Unit = {}
) {
    TextField(
        value = valueState.value,
        onValueChange = { valueState.value = it },
        modifier = Modifier
            .padding(padding)
//            .background(backgroundColor)
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(borderRadius)),
        enabled = enabled,
        readOnly = readOnly,
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            backgroundColor = backgroundColor,
            disabledTextColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        label = {
            Text(
                text = hint,
                color = PrimaryColor
            )
        },
        leadingIcon = {
            if (haveSearchIcon) {
                IconButton(
                    onClick = { searchOnTap() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = PrimaryColor
                    )
                }
            }
        }
    )
}