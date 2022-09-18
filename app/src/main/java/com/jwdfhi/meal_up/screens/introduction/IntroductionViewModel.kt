package com.jwdfhi.meal_up.screens.introduction

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.jwdfhi.meal_up.R
import com.jwdfhi.meal_up.models.CustomViewModel
import com.jwdfhi.meal_up.models.IntroductionItemModel
import com.jwdfhi.meal_up.screens.Screens
import com.jwdfhi.meal_up.utils.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroductionViewModel @Inject constructor(
    val context: Context,
) : ViewModel(), CustomViewModel<Nothing, Nothing, Nothing> {

    override fun onBackPressed(navController: NavController) {
        navController.popBackStack()
    }

    val introductionItems: List<IntroductionItemModel> = listOf<IntroductionItemModel>(
        IntroductionItemModel(
            title = "Healthy food",
            description = "A variety of healty foods made by the best chefs. Ingredients are easy to find, all delicious flavors can only be found at Meal up",
            backgroundImage = R.drawable.introduction_background_resized_1,
            color = Color(0xFFE43535)
        ),
        IntroductionItemModel(
            title = "Stay fresh",
            description = "Not only food, we clear healthy drink options for you. Fresh taste always accompanies you",
            backgroundImage = R.drawable.introduction_background_resized_4,
            color = Color(0xFFD59923)
        ),
        IntroductionItemModel(
            title = "Let's Cooking",
            description = "Are you ready to make a dish for your friends or family? get start and cook",
            backgroundImage = R.drawable.introduction_background_resized_5,
            color = Color(0xFF26D53D)
        )
    )

    @ExperimentalPagerApi
    public fun navigateToNextHorizontalPage(
        navController: NavController,
        scope: CoroutineScope,
        horizontalPageViewState: PagerState
    ): Unit {
        val isLastItem: Boolean = horizontalPageViewState.currentPage + 1 == introductionItems.size

        when (isLastItem) {
            false -> scope.launch {
                horizontalPageViewState.animateScrollToPage(horizontalPageViewState.currentPage + 1)
            }
            true -> saveStatusInSharedPreferencesAndNavigateToHomeScreen(navController)
        }

    }

    fun saveStatusInSharedPreferencesAndNavigateToHomeScreen(navController: NavController) {
        saveStatusInSharedPreferences()
        navController.navigate(Screens.HomeScreen.name)
    }

    private fun saveStatusInSharedPreferences() {
        val editor = context.getSharedPreferences(Constant.MANAGEMENT_SETTING_KEY, Context.MODE_PRIVATE).edit()

        editor.putBoolean(Constant.INTRODUCTION_IS_VIEWED_KEY, true)
        editor.commit()
    }



}