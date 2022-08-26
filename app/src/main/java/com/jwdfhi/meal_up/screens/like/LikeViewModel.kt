package com.jwdfhi.meal_up.screens.like

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.jwdfhi.meal_up.models.CustomViewModel
import com.jwdfhi.meal_up.models.DataOrException
import com.jwdfhi.meal_up.models.DataOrExceptionStatus
import com.jwdfhi.meal_up.models.MealModel
import com.jwdfhi.meal_up.repositories.MealDatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LikeViewModel @Inject constructor(
    public val context: Context,
    private val mealDatabaseRepository: MealDatabaseRepository
) : ViewModel(), CustomViewModel<Nothing, Nothing, Nothing> {

    override fun initState() {
        viewModelScope.launch(Dispatchers.IO) {
            _mealsDataOrException.value = DataOrException(status = DataOrExceptionStatus.Loading)

            val likedMeals = getLikedMeals()

            _mealsDataOrException.value = DataOrException(
                data = likedMeals,
                status = DataOrExceptionStatus.Success
            )
        }
    }

    override fun onBackPressed(navController: NavController) {
        navController.popBackStack()
    }

    private val _mealsDataOrException = MutableStateFlow<DataOrException<MutableList<MealModel>>>(
        DataOrException(status = DataOrExceptionStatus.Loading)
    )
    val mealsDataOrException = _mealsDataOrException.asStateFlow()
    private suspend fun getLikedMeals(): MutableList<MealModel> {
        var likedMeals = emptyList<MealModel>()
        mealDatabaseRepository.getMeals().collect {
            likedMeals = it
        }

        return likedMeals.toMutableList()
    }

    fun searchMealByName(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _mealsDataOrException.value = DataOrException(status = DataOrExceptionStatus.Loading)

            val likedMeals = getLikedMeals()

            if (value.trim().isEmpty()) {
                _mealsDataOrException.value = DataOrException(
                    data = likedMeals,
                    status = DataOrExceptionStatus.Success
                )
                return@launch
            }

            val filteredByNameMeals = mutableListOf<MealModel>()
            _mealsDataOrException.value.data?.forEach { storedMeal ->
                if (storedMeal.strMeal.contains(value.trim())) {
                    filteredByNameMeals.add(storedMeal)
                }
            }

            _mealsDataOrException.value = DataOrException(
                data = filteredByNameMeals,
                status = DataOrExceptionStatus.Success
            )
        }
    }

}