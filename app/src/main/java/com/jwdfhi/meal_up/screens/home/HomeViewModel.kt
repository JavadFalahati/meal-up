package com.jwdfhi.meal_up.screens.home

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jwdfhi.meal_up.models.DataOrException
import com.jwdfhi.meal_up.models.LikedMealModel
import com.jwdfhi.meal_up.models.MarkedMealModel
import com.jwdfhi.meal_up.models.RandomMealServiceModel
import com.jwdfhi.meal_up.repositories.MealDatabaseRepository
import com.jwdfhi.meal_up.repositories.MealServiceRepository
import com.jwdfhi.meal_up.utils.deviceHaveConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.net.SocketException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val context: Context,
    private val mealServiceRepository: MealServiceRepository,
    private val mealDatabaseRepository: MealDatabaseRepository
) : ViewModel() {

    private val _likedMealList = MutableStateFlow<List<LikedMealModel>>(emptyList())
    val likedMealList = _likedMealList.asStateFlow()

    private val _markedMealList = MutableStateFlow<List<MarkedMealModel>>(emptyList())
    val markedMealList = _markedMealList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            mealDatabaseRepository.getLikedMeals().distinctUntilChanged().collect() {
                _likedMealList.value = it
            }

            mealDatabaseRepository.getMarkedMeals().distinctUntilChanged().collect() {
                _markedMealList.value = it
            }
        }
    }

    suspend fun getRandomMeals(): DataOrException<MutableList<RandomMealServiceModel>, Boolean, Exception> {
        if (!deviceHaveConnection(context)) { return DataOrException(exception = Exception(message = "Can't Connect to the Server!")) }

        val mealList: MutableList<RandomMealServiceModel> = emptyList<RandomMealServiceModel>() as MutableList<RandomMealServiceModel>

        for (i in 0..10) {
            val dataOrException: DataOrException<RandomMealServiceModel, Boolean, Exception>
                = mealServiceRepository.getRandomMeal()
            val exception: Exception? = dataOrException.exception

            if (exception != null) { return DataOrException(exception = exception) }

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

            mealList.add(dataOrException.data!!)
        }

        return DataOrException(data = mealList)
    }

}

