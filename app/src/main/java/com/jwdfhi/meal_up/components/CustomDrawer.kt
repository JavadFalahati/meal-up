package com.jwdfhi.meal_up.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jwdfhi.meal_up.R
import com.jwdfhi.meal_up.components.CustomBackPressHandler
import com.jwdfhi.meal_up.models.DrawerItemModel
import com.jwdfhi.meal_up.screens.Screens
import com.jwdfhi.meal_up.ui.theme.*
import com.slaviboy.composeunits.sh

@Composable
fun CustomDrawer(
    navController: NavController,
    screenName: String,
    onCloseDrawer: () -> Unit
) {

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
                                onTap = { onCloseDrawer() }
                            ),
                            DrawerItemModel(
                                title = "Likes",
                                screenName = Screens.LikeScreen.name,
                                icon = R.drawable.fill_like_icon_1,
                                onTap = { navController.navigate(Screens.LikeScreen.name) }
                            ),
                            DrawerItemModel(
                                title = "Marks",
                                screenName = Screens.MarkScreen.name,
                                icon = R.drawable.fill_mark_icon_1,
                                onTap = { navController.navigate(Screens.MarkScreen.name) }
                            ),
                            DrawerItemModel(
                                title = "Management",
                                screenName = Screens.ManagementScreen.name,
                                icon = R.drawable.management_icon_1,
                                onTap = { navController.navigate(Screens.ManagementScreen.name) }
                            ),
                        )
                    ) {
                        HomeScreenDrawerItem(
                            item = it,
                            backgroundColor =
                            if (screenName == it.screenName)
                                Primary60Color.copy(0.6f)
                            else
                                TransparentColor,
                            margin = 0.dp
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
                    color = Black60Color    ,
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
                                onTap = { /*TODO: Open a link of privacy and polycy*/ }
                            ),
                        )
                    ) {
                        HomeScreenDrawerItem(
                            item = it,
                            margin = 0.dp
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
            color = PrimaryColor,
            fontWeight = FontWeight.W500,
            fontSize = 0.022.sh
        ),
        modifier = Modifier
            .clip(shape = RoundedCornerShape(4.dp))
            .background(LightPrimaryColor)
            .fillMaxWidth()
            .padding(
                horizontal = 8.dp,
                vertical = 10.dp
            )
    )
}