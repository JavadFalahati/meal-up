package com.jwdfhi.meal_up.screens.home

import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class HomeViewModel @Inject constructor(
    public val context: Context,
    private val mealServiceRepository: MealServiceRepository,
    private val mealDatabaseRepository: MealDatabaseRepository
) : ViewModel() {

    private val onBackPressedTimer = object: CountDownTimer(3000, 1000) {
        override fun onTick(millisUntilFinished: Long) {}
        override fun onFinish() { onBackPressedTimerIsFinished = true }
    }
    var onBackPressedTimerIsFinished: Boolean = true
    fun startOnBackPressedTimer() {
        onBackPressedTimerIsFinished = false
        onBackPressedTimer.start()
    }


    private var _mealsDataOrException:
            MutableStateFlow<DataOrException<MutableList<RandomMealServiceModel.Meal>, Exception, DataOrExceptionStatus>> = MutableStateFlow(
                DataOrException(status = DataOrExceptionStatus.Loading)
            )
    val mealsDataOrException = _mealsDataOrException.asStateFlow()

    private val _likedMealList = MutableStateFlow<List<LikedMealModel>>(emptyList())
    val likedMealList = _likedMealList.asStateFlow()

    private val _markedMealList = MutableStateFlow<List<MarkedMealModel>>(emptyList())
    val markedMealList = _markedMealList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
//            mealDatabaseRepository.getLikedMeals().distinctUntilChanged().collect() {
//                _likedMealList.value = it
//            }
//
//            mealDatabaseRepository.getMarkedMeals().distinctUntilChanged().collect() {
//                _markedMealList.value = it
//            }

            getRandomMeals()
        }
    }

    suspend fun getRandomMeals(): Unit {
        _mealsDataOrException.value = DataOrException(status = DataOrExceptionStatus.Loading)

//        if (!deviceHaveConnection(context)) {
//            _mealsDataOrException.value = DataOrException(
//                exception = Exception(/*message = "Can't Connect to the Server!"*/),
//                status = DataOrExceptionStatus.Failure
//            )
//            Log.d("ITS JAVAD", "exception two")
//            return
//        }

        val mealList = mutableListOf<RandomMealServiceModel.Meal>()

        var repeatCount: Int = 10
        while (repeatCount > 0) {
            var isRepetitive = false

            val dataOrException = getSingleRandomMeal()
            if (dataOrException.status != DataOrExceptionStatus.Success) { return }

            val id: String = dataOrException.data!!.meals[0].idMeal
            mealList.forEach {
                if (id == it.idMeal) {
                    isRepetitive = true
                    return@forEach
                }
            }

            if (!isRepetitive) {
                repeatCount--
                mealList.add(dataOrException.data!!.meals[0])
            }
        }

        _mealsDataOrException.value = DataOrException(
            data = mealList,
            status = DataOrExceptionStatus.Success
        )
    }

    private suspend fun getSingleRandomMeal(): DataOrException<RandomMealServiceModel, Exception, DataOrExceptionStatus> {
        val dataOrException = mealServiceRepository.getRandomMeal()

        val exception: Exception? = dataOrException.exception

        if (exception != null) {
            _mealsDataOrException.value = DataOrException(
                exception = exception,
                status = DataOrExceptionStatus.Failure
            )
            dataOrException.status = DataOrExceptionStatus.Failure
            return dataOrException
        }

        _likedMealList.value.forEach {
            if (dataOrException.data!!.meals[0].idMeal == it.idMeal) {
                dataOrException.data!!.meals[0].isLiked = true
            }
        }

        _markedMealList.value.forEach {
            if (dataOrException.data!!.meals[0].idMeal == it.idMeal) {
                dataOrException.data!!.meals[0].isMarked = true
                dataOrException.data!!.meals[0].markName = it.markName
                dataOrException.data!!.meals[0].markColor = it.markColor
            }
        }

        dataOrException.status = DataOrExceptionStatus.Success
        return dataOrException
    }

    suspend fun getMealByName(name: String): Unit {
        _mealsDataOrException.value = DataOrException(status = DataOrExceptionStatus.Loading)
        _mealsDataOrException.value.data?.clear()

        val dataOrException = mealServiceRepository.getMealByName(name = name.trim())
        if (dataOrException.exception != null) {
            _mealsDataOrException.value = DataOrException(
                exception = dataOrException.exception,
                status = DataOrExceptionStatus.Failure
            )
            return
        }

        if (dataOrException.data?.meals == null) { dataOrException.data?.meals = emptyList() }
        _mealsDataOrException.value = DataOrException(
            data = (dataOrException.data?.meals)?.toMutableList(),
            status = DataOrExceptionStatus.Success
        )
    }

}

