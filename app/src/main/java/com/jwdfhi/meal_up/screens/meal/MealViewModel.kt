package com.jwdfhi.meal_up.screens.meal

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.jwdfhi.meal_up.models.*
import com.jwdfhi.meal_up.repositories.MealDatabaseRepository
import com.jwdfhi.meal_up.repositories.MealServiceRepository
import com.jwdfhi.meal_up.ui.theme.White100Color
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(
    public val context: Context,
    private val mealDatabaseRepository: MealDatabaseRepository,
    private val mealServiceRepository: MealServiceRepository
) : ViewModel(), CustomViewModel<String, Nothing> {

    override fun initState(id: String) {
        viewModelScope.launch(Dispatchers.IO) { getMeal(id) }
    }

    override fun onBackPressed(navController: NavController) {
        navController.popBackStack()
    }

    private val _mealDataOrException = MutableStateFlow<DataOrException<MutableState<MealModel>>>(
        DataOrException(status = DataOrExceptionStatus.Loading)
    )
    val mealDataOrException = _mealDataOrException.asStateFlow()
    private suspend fun getMeal(id: String) {
        _mealDataOrException.value = DataOrException(status = DataOrExceptionStatus.Loading)

        val networkMealDataOrException = mealServiceRepository.getMealDetail(id)
        val storedMeal = getMealFromDatabase(id)

        _mealDataOrException.value =
            if (networkMealDataOrException.status != DataOrExceptionStatus.Failure && storedMeal != null)
                DataOrException(data = mutableStateOf(storedMeal), status = DataOrExceptionStatus.Success)
            else
                DataOrException(data = mutableStateOf(networkMealDataOrException.data!!), status = DataOrExceptionStatus.Success)

    }

    fun likeOrUnLikeMeal() {
        if (_mealDataOrException.value.data == null) { return }

        val updatedMealModel = _mealDataOrException.value.data!!.value
        updatedMealModel.isLiked = !updatedMealModel.isLiked

        checkMealIsExistThenUpsert(_mealDataOrException.value.data!!.value, updatedMealModel).let {
            _mealDataOrException.value = DataOrException(data = mutableStateOf(updatedMealModel), status = DataOrExceptionStatus.Success)
        }
    }

    fun markMeal(markModel: MarkModel): Unit {
        if (_mealDataOrException.value.data == null) { return }

        val updatedMealModel = _mealDataOrException.value.data!!.value
        updatedMealModel.isMarked = true
        updatedMealModel.markName = markModel.name
        updatedMealModel.markColor = markModel.color

        checkMealIsExistThenUpsert(_mealDataOrException.value.data!!.value, updatedMealModel).let {
            _mealDataOrException.value = DataOrException(data = mutableStateOf(updatedMealModel), status = DataOrExceptionStatus.Success)
        }
    }

    fun unMarkMeal(): Unit {
        if (_mealDataOrException.value.data == null) { return }

        val updatedMealModel = _mealDataOrException.value.data!!.value
        updatedMealModel.isMarked = false
        updatedMealModel.markName = ""
        updatedMealModel.markColor = White100Color.value.toInt()

        checkMealIsExistThenUpsert(_mealDataOrException.value.data!!.value, updatedMealModel).let {
            _mealDataOrException.value = DataOrException(data = mutableStateOf(updatedMealModel), status = DataOrExceptionStatus.Success)
        }
    }

    private fun checkMealIsExistThenUpsert(mealModel: MealModel, updatedMealModel: MealModel): Unit {
        viewModelScope.launch(Dispatchers.IO) {
            when (getMealFromDatabase(mealModel.idMeal) != null) {
                false -> insertMealOnDatabase(updatedMealModel)
                true -> updateMealOnDatabase(updatedMealModel)
            }
        }
    }

    private suspend fun getMealFromDatabase(id: String): MealModel? = mealDatabaseRepository.getMealById(id)

    private suspend fun insertMealOnDatabase(mealModel: MealModel) = mealDatabaseRepository.insertMeal(mealModel)

    private suspend fun updateMealOnDatabase(mealModel: MealModel) = mealDatabaseRepository.updateMeal(mealModel)

}