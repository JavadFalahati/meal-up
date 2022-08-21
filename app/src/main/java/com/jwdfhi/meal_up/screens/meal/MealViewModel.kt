package com.jwdfhi.meal_up.screens.meal

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.jwdfhi.meal_up.models.*
import com.jwdfhi.meal_up.repositories.MealDatabaseRepository
import com.jwdfhi.meal_up.repositories.MealServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(
    private val context: Context,
    private val mealDatabaseRepository: MealDatabaseRepository,
    private val mealServiceRepository: MealServiceRepository
) : ViewModel(), CustomViewModel<String> {

    override fun initState(id: String) {
        viewModelScope.launch(Dispatchers.IO) { getMeal(id) }
    }

    override fun onBackPressed(navController: NavController) {
        navController.popBackStack()
    }

    private val _mealDataOrException = MutableStateFlow<DataOrException<MealModel>>(
        DataOrException(status = DataOrExceptionStatus.Loading)
    )
    val mealDataOrException = _mealDataOrException.asStateFlow()
    private suspend fun getMeal(id: String) {
        _mealDataOrException.value = DataOrException(status = DataOrExceptionStatus.Loading)

        val networkMealDataOrException = mealServiceRepository.getMealDetail(id)
        val storedMeal = getMealFromDatabase(id)

        _mealDataOrException.value =
            if (networkMealDataOrException.status == DataOrExceptionStatus.Success && storedMeal != null)
                DataOrException(data = storedMeal, status = DataOrExceptionStatus.Success)
            else
                networkMealDataOrException
    }

    fun likeMeal(mealModel: MealModel) = checkMealIsExitThenUpsert(mealModel)

    fun markMeal(mealModel: MealModel) = checkMealIsExitThenUpsert(mealModel)

    private fun checkMealIsExitThenUpsert(mealModel: MealModel) {
        viewModelScope.launch(Dispatchers.IO) {
            when (getMealFromDatabase(mealModel.idMeal) != null) {
                false -> insertMealOnDatabase(mealModel)
                true -> updateMealOnDatabase(mealModel)
            }
        }
    }

    private suspend fun getMealFromDatabase(id: String): MealModel? = mealDatabaseRepository.getMealById(id)

    private suspend fun insertMealOnDatabase(mealModel: MealModel) = mealDatabaseRepository.insertMeal(mealModel)

    private suspend fun updateMealOnDatabase(mealModel: MealModel) = mealDatabaseRepository.updateMeal(mealModel)

}