package com.jwdfhi.meal_up.models

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector

data class DrawerItemModel(
    val title: String,
    val screenName: String,
    val icon: Int,
    val onTap: () -> Unit = {}
)
