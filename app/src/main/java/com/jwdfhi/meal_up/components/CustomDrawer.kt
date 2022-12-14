package com.jwdfhi.meal_up.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jwdfhi.meal_up.R
import com.jwdfhi.meal_up.components.CustomBackPressHandler
import com.jwdfhi.meal_up.models.DrawerItemModel
import com.jwdfhi.meal_up.screens.Screens
import com.jwdfhi.meal_up.ui.theme.*
import com.jwdfhi.meal_up.utils.ManagementSettings
import com.jwdfhi.meal_up.utils.openPrivacyAndPolicyWebsite
import com.slaviboy.composeunits.sh

@Composable
fun CustomDrawer(
    navController: NavController,
    screenName: String,
    onCloseDrawer: () -> Unit
) {
    val context = LocalContext.current

    CustomBackPressHandler(onBackPressed = { onCloseDrawer() })

    Surface(
        modifier = Modifier
            .background(color = White100Color)
            .fillMaxSize()
            .padding(6.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
            ) {
                Spacer(modifier = Modifier.height(4.dp))
                HomeScreenDrawerTitleStyle(title = "All")
                Spacer(modifier = Modifier.height(6.dp))
                LazyColumn() {
                    items(
                        listOf<DrawerItemModel>(
                            DrawerItemModel(
                                title = "Home",
                                screenName = Screens.HomeScreen.name,
                                icon = R.drawable.home_icon_1,
                            ),
                            DrawerItemModel(
                                title = "Likes",
                                screenName = Screens.LikeScreen.name,
                                icon = R.drawable.fill_like_icon_1,
                            ),
                            DrawerItemModel(
                                title = "Marks",
                                screenName = Screens.MarkScreen.name,
                                icon = R.drawable.fill_mark_icon_1,
                            ),
                            DrawerItemModel(
                                title = "Management",
                                screenName = Screens.ManagementScreen.name,
                                icon = R.drawable.management_icon_1,
                            ),
                        )
                    ) {
                        CustomDrawerItem(
                            item = it,
                            backgroundColor =
                            if (screenName == it.screenName)
                                ManagementSettings.Primary60Color.copy(0.2f)
                            else
                                TransparentColor,
                            margin = 0.dp,
                            onTap = {
                                if (it.screenName == screenName) onCloseDrawer()
                                else navController.navigate(it.screenName) {
                                    popUpTo(0)
                                }
                            }
                        )
                    }
                }
            }
            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.Start,
            ) {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Black60Color,
                    thickness = 1.5.dp
                )
                Spacer(modifier = Modifier.height(10.dp))
                HomeScreenDrawerTitleStyle(title = "About")
                Spacer(modifier = Modifier.height(6.dp))
                LazyColumn() {
                    items(
                        listOf<DrawerItemModel>(
                            DrawerItemModel(
                                title = "Privacy and policy",
                                screenName = "",
                                icon = R.drawable.fill_privacy_policy_icon_1,
                                onTap = { openPrivacyAndPolicyWebsite(context) }
                            ),
                        )
                    ) {
                        CustomDrawerItem(
                            item = it,
                            margin = 0.dp,
                            onTap = null
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
private fun HomeScreenDrawerTitleStyle(title: String) {
    Text(
        text = title,
        style = TextStyle(
            color = ManagementSettings.PrimaryColor,
            fontWeight = FontWeight.W500,
            fontSize = 0.022.sh
        ),
        modifier = Modifier
            .clip(shape = RoundedCornerShape(4.dp))
            .background(ManagementSettings.LightPrimaryColor)
            .fillMaxWidth()
            .padding(
                horizontal = 8.dp,
                vertical = 10.dp
            )
    )
}