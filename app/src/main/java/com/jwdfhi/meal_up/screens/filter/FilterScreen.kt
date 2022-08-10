package com.jwdfhi.meal_up.screens.filter

import android.util.Log
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.jwdfhi.meal_up.components.*
import com.jwdfhi.meal_up.models.DataOrExceptionStatus
import com.jwdfhi.meal_up.models.LoadingType
import com.jwdfhi.meal_up.screens.filter.components.FilterAppbar
import com.jwdfhi.meal_up.screens.home.components.HomeScreenDrawer
import com.jwdfhi.meal_up.screens.home.components.HomeScreenSearchAndFilter
import com.jwdfhi.meal_up.screens.home.onBackPressed
import com.jwdfhi.meal_up.screens.home.search
import com.jwdfhi.meal_up.ui.theme.PrimaryColor
import com.jwdfhi.meal_up.ui.theme.White100Color
import com.slaviboy.composeunits.dw
import com.slaviboy.composeunits.sh
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun FilterScreen(
    navController: NavController
) {

    CustomBackPressHandler(onBackPressed = {})

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFECECEC)),
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFECECEC))
                .padding(
                    horizontal = 12.dp
                ),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.weight(0.3f))
                Box(modifier = Modifier.weight(1.0f)) {
                    FilterAppbar(
                        navController = navController,
                        onClear = { /*TODO: onClear*/ }
                    )
                }
                Spacer(modifier = Modifier.weight(0.1f))
                Box(
                    modifier = Modifier
                        .weight(9f)
                        .align(Alignment.CenterHorizontally)
                ) {
                    LazyColumn() {
                        // TODO: Implement items and get the real data from viewModel thath you goinng to create
                    }
                }
            }
            Button(
                modifier = Modifier
                    .width(width = 0.7.dw)
                    .align(Alignment.CenterHorizontally)
                    .clip(shape = RoundedCornerShape(6.dp)),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = PrimaryColor
                ),
                onClick = { /*TODO: Submit filter*/ },
            ) {
                Text(
                    text = "Submit",
                    style = TextStyle(
                        color = White100Color,
                        fontSize = 0.02.sh
                    ),
                    modifier = Modifier
                        .padding(3.dp)
                )
            }
        }
    }
}