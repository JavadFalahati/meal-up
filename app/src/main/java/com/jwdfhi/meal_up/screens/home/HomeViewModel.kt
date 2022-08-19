package com.jwdfhi.meal_up.screens.home

import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jwdfhi.meal_up.models.*
import com.jwdfhi.meal_up.repositories.MealDatabaseRepository
import com.jwdfhi.meal_up.repositories.MealServiceRepository
import com.jwdfhi.meal_up.utils.FilterListSelectedItemHelper
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
            MutableStateFlow<DataOrException<MutableList<FilteredMealModel>>> = MutableStateFlow(
                DataOrException(status = DataOrExceptionStatus.Loading)
            )
    val mealsDataOrException = _mealsDataOrException.asStateFlow()

    private val _likedMealList = MutableStateFlow<List<LikedMealModel>>(emptyList())
    val likedMealList = _likedMealList.asStateFlow()

    private val _markedMealList = MutableStateFlow<List<MarkedMealModel>>(emptyList())
    val markedMealList = _markedMealList.asStateFlow()

    fun initState(filterListSelectedItemTextModel: FilterListSelectedItemTextModel): Unit {

        viewModelScope.launch(Dispatchers.IO) {
            // mealDatabaseRepository.getLikedMeals().distinctUntilChanged().collect() {
            //     _likedMealList.value = it
            // }
            //
            // mealDatabaseRepository.getMarkedMeals().distinctUntilChanged().collect() {
            //     _markedMealList.value = it
            // }

            Log.d("getAllFilteredMeals", filterListSelectedItemTextModel.toString())

            if (FilterListSelectedItemHelper.isNotEmpty(filterListSelectedItemTextModel)) {
                getAllFilteredMeals(filterListSelectedItemTextModel)
            }
            else { getRandomMeals() }

        }

    }

    suspend fun getRandomMeals(): Unit {
        _mealsDataOrException.value = DataOrException(status = DataOrExceptionStatus.Loading)

        // if (!deviceHaveConnection(context)) {
        //     _mealsDataOrException.value = DataOrException(
        //         exception = Exception(/*message = "Can't Connect to the Server!"*/),
        //         status = DataOrExceptionStatus.Failure
        //     )
        //     Log.d("ITS JAVAD", "exception two")
        //     return
        // }

        val mealList = mutableListOf<FilteredMealModel>()

        var repeatCount: Int = 10
        while (repeatCount > 0) {
            var isRepetitive = false

            val dataOrException = getSingleRandomMeal()
            if (dataOrException.status != DataOrExceptionStatus.Success) { return }

            val id: String = dataOrException.data!![0].idMeal
            mealList.forEach {
                if (id == it.idMeal) {
                    isRepetitive = true
                    return@forEach
                }
            }

            if (!isRepetitive) {
                repeatCount--
                mealList.add(dataOrException.data!![0])
            }
        }

        _mealsDataOrException.value = DataOrException(
            data = mealList,
            status = DataOrExceptionStatus.Success
        )
    }

    private suspend fun getSingleRandomMeal(): DataOrException<MutableList<FilteredMealModel>> {
        val dataOrException = mealServiceRepository.getRandomMeal()

        val exception: Exception? = dataOrException.exception

        if (exception != null) {
            _mealsDataOrException.value = DataOrException(
                exception = exception,
                status = DataOrExceptionStatus.Failure
            )

            return DataOrException(
                status = DataOrExceptionStatus.Failure
            )
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

        val filteredMealModel = mutableListOf<FilteredMealModel>()
        dataOrException.data?.meals?.forEach {
            filteredMealModel.add(FilteredMealModel(
                idMeal = it.idMeal,
                strMeal = it.strMeal,
                strMealThumb = it.strMealThumb
            ))
        }

        return DataOrException(
            data = filteredMealModel,
            status = DataOrExceptionStatus.Success
        )
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

        val filteredMealModel = mutableListOf<FilteredMealModel>()
        dataOrException.data?.meals?.forEach {
            filteredMealModel.add(FilteredMealModel(
                idMeal = it.idMeal,
                strMeal = it.strMeal,
                strMealThumb = it.strMealThumb
            ))
        }

        _mealsDataOrException.value = DataOrException(
            data = (filteredMealModel).toMutableList(),
            status = DataOrExceptionStatus.Success
        )
    }

    private suspend fun getAllFilteredMeals(filterListSelectedItemTextModel: FilterListSelectedItemTextModel): Unit {
        var filteredMealModel: MutableList<FilteredMealModel> = mutableListOf()

        val mealsByCategory = getSingleFilteredMeals<MutableList<MealListByCategoryServiceModel.Meal>>(
            filterType = FilterType.Category,
            filteredMealModelList = filteredMealModel,
            filterListSelectedItemTextModel = filterListSelectedItemTextModel
        )
        if (mealsByCategory.status == DataOrExceptionStatus.Failure) { return }
        filteredMealModel = mealsByCategory.data!!

        val mealsByIngredients = getSingleFilteredMeals<MutableList<MealListByIngredientServiceModel.Meal>>(
            filteredMealModelList = filteredMealModel,
            filterType = FilterType.Ingredient,
            filterListSelectedItemTextModel = filterListSelectedItemTextModel
        )
        if (mealsByIngredients.status == DataOrExceptionStatus.Failure) { return }
        filteredMealModel = mealsByIngredients.data!!

        val mealsByArea = getSingleFilteredMeals<MutableList<MealListByAreaServiceModel.Meal>>(
            filteredMealModelList = filteredMealModel,
            filterType = FilterType.Area,
            filterListSelectedItemTextModel = filterListSelectedItemTextModel
        )
        if (mealsByArea.status == DataOrExceptionStatus.Failure) { return }
        filteredMealModel = mealsByArea.data!!

        _mealsDataOrException.value = DataOrException(
            data = filteredMealModel,
            status = DataOrExceptionStatus.Success
        )
    }

    private suspend fun <T> getSingleFilteredMeals(
        filterType: FilterType,
        filteredMealModelList: MutableList<FilteredMealModel>,
        filterListSelectedItemTextModel: FilterListSelectedItemTextModel
    ): DataOrException<MutableList<FilteredMealModel>> {
        val notCheckingPreviousFilteredList = FilterListSelectedItemHelper.notCheckingPreviousFilteredList(
            filterType = filterType,
            filterListSelectedItemTextModel = filterListSelectedItemTextModel
        )

        when (filterType) {
            FilterType.Category -> {
                if (filterListSelectedItemTextModel.category.isNotEmpty()) {
                    val dataOrException =
                        mealServiceRepository.getFilteredMealListByCategory(filterListSelectedItemTextModel.category)

                    if (dataOrException.status == DataOrExceptionStatus.Failure) {
                        _mealsDataOrException.value = DataOrException(
                            exception = dataOrException.exception,
                            status = DataOrExceptionStatus.Failure
                        )
                        return DataOrException(
                            exception = dataOrException.exception,
                            status = DataOrExceptionStatus.Failure
                        )
                    }

                    val jointMealList = getJointFilteredMealList(
                        filterType = filterType,
                        notCheckingPreviousFilteredList = notCheckingPreviousFilteredList,
                        mealList = dataOrException.data!!.meals,
                        filteredMealModelList = filteredMealModelList
                    )

                    return DataOrException(data = jointMealList)
                }
                else { return DataOrException(data = filteredMealModelList) }
            }
            FilterType.Ingredient -> {
                if (filterListSelectedItemTextModel.ingredients.isNotEmpty()) {
                    var jointMealList = filteredMealModelList

                    filterListSelectedItemTextModel.ingredients.forEach {
                        val dataOrException =
                            mealServiceRepository.getFilteredMealListByIngredient(it)

                        if (dataOrException.status == DataOrExceptionStatus.Failure) {
                            _mealsDataOrException.value = DataOrException(
                                exception = dataOrException.exception,
                                status = DataOrExceptionStatus.Failure
                            )
                            return DataOrException(
                                exception = dataOrException.exception,
                                status = DataOrExceptionStatus.Failure
                            )
                        }

                        jointMealList = getJointFilteredMealList(
                            filterType = filterType,
                            notCheckingPreviousFilteredList = notCheckingPreviousFilteredList,
                            mealList = dataOrException.data!!.meals,
                            filteredMealModelList = jointMealList
                        )

                    }

                    return DataOrException(data = jointMealList)
                }
                else { return DataOrException(data = filteredMealModelList) }
            }
            FilterType.Area -> {
                if (filterListSelectedItemTextModel.area.isNotEmpty()) {
                    val dataOrException =
                        mealServiceRepository.getFilteredMealListByArea(filterListSelectedItemTextModel.area)

                    if (dataOrException.status == DataOrExceptionStatus.Failure) {
                        _mealsDataOrException.value = DataOrException(
                            exception = dataOrException.exception,
                            status = DataOrExceptionStatus.Failure
                        )
                        return DataOrException(
                            exception = dataOrException.exception,
                            status = DataOrExceptionStatus.Failure
                        )
                    }

                    val jointMealList = getJointFilteredMealList(
                        filterType = filterType,
                        notCheckingPreviousFilteredList = notCheckingPreviousFilteredList,
                        mealList = dataOrException.data!!.meals,
                        filteredMealModelList = filteredMealModelList
                    )

                    return DataOrException(data = jointMealList)
                }
                else { return DataOrException(data = filteredMealModelList) }
            }
        }
    }

    private fun <T> getJointFilteredMealList(
        filterType: FilterType,
        notCheckingPreviousFilteredList: Boolean,
        filteredMealModelList: MutableList<FilteredMealModel>,
        mealList: List<T>,
    ): MutableList<FilteredMealModel> {
        when (filterType) {
            FilterType.Category -> {
                val jointMealList = mutableListOf<FilteredMealModel>()

                (mealList as List<MealListByCategoryServiceModel.Meal>).forEach { meal ->
                    if (notCheckingPreviousFilteredList) {
                        jointMealList.add(FilteredMealModel(
                            idMeal = meal.idMeal,
                            strMeal = meal.strMeal,
                            strMealThumb = meal.strMealThumb,
                        ))
                    }
                    else {
                        filteredMealModelList.forEach { filteredMeal ->
                            if (meal.idMeal == filteredMeal.idMeal) { jointMealList.add(filteredMeal) }
                        }
                    }

                }

                return jointMealList
            }
            FilterType.Ingredient -> {
                val jointMealList = mutableListOf<FilteredMealModel>()

                (mealList as List<MealListByIngredientServiceModel.Meal>).forEach { meal ->
                    if (notCheckingPreviousFilteredList) {
                        jointMealList.add(FilteredMealModel(
                            idMeal = meal.idMeal,
                            strMeal = meal.strMeal,
                            strMealThumb = meal.strMealThumb,
                        ))
                    }
                    else {
                        filteredMealModelList.forEach { filteredMeal ->
                            if (meal.idMeal == filteredMeal.idMeal) { jointMealList.add(filteredMeal) }
                        }
                    }
                }

                return jointMealList
            }
            FilterType.Area -> {
                val jointMealList = mutableListOf<FilteredMealModel>()

                (mealList as List<MealListByAreaServiceModel.Meal>).forEach { meal ->
                    if (notCheckingPreviousFilteredList) {
                        jointMealList.add(FilteredMealModel(
                            idMeal = meal.idMeal,
                            strMeal = meal.strMeal,
                            strMealThumb = meal.strMealThumb,
                        ))
                    }
                    else {
                        filteredMealModelList.forEach { filteredMeal ->
                            if (meal.idMeal == filteredMeal.idMeal) { jointMealList.add(filteredMeal) }
                        }
                    }
                }

                return jointMealList
            }
        }
    }

}

