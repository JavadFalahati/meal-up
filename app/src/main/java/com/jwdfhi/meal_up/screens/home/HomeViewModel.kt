package com.jwdfhi.meal_up.screens.home

import android.content.Context
import android.os.CountDownTimer
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jwdfhi.meal_up.models.*
import com.jwdfhi.meal_up.repositories.MealDatabaseRepository
import com.jwdfhi.meal_up.repositories.MealServiceRepository
import com.jwdfhi.meal_up.ui.theme.TransparentColor
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

    var filterListSelectedItemModel = FilterListSelectedItemModel()

    fun initState(filterListSelectedItemModel: FilterListSelectedItemModel): Unit {
        this.filterListSelectedItemModel = filterListSelectedItemModel

        viewModelScope.launch(Dispatchers.IO) {
            // mealDatabaseRepository.getLikedMeals().distinctUntilChanged().collect() {
            //     _likedMealList.value = it
            // }
            //
            // mealDatabaseRepository.getMarkedMeals().distinctUntilChanged().collect() {
            //     _markedMealList.value = it
            // }

            if (FilterListSelectedItemHelper.isNotEmpty(filterListSelectedItemModel)) {
                getAllFilteredMeals(filterListSelectedItemModel)
            }
            else { getRandomMeals() }

        }

    }

    private suspend fun getRandomMeals(): Unit {
        _mealsDataOrException.value = DataOrException(status = DataOrExceptionStatus.Loading)

        // if (!deviceHaveConnection(context)) {
        //     _mealsDataOrException.value = DataOrException(
        //         exception = Exception(/*message = "Can't Connect to the Server!"*/),
        //         status = DataOrExceptionStatus.Failure
        //     )
        //     Log.d("ITS JAVAD", "exception two")
        //     return
        // }

        var mealList = mutableListOf<FilteredMealModel>()

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

        mealList = checkDataMealsWithMarkedMeals(mealList)
        mealList = checkDataMealsWithLikedMeals(mealList)

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

    suspend fun getMealByName(
        filterListSelectedItemModel: FilterListSelectedItemModel,
        name: String
    ): Unit {
        if (name.trim().isEmpty() && !FilterListSelectedItemHelper.isNotEmpty(filterListSelectedItemModel = filterListSelectedItemModel)) {
            getRandomMeals()
            return
        }
        if (name.trim().isEmpty() && FilterListSelectedItemHelper.isNotEmpty(filterListSelectedItemModel = filterListSelectedItemModel)) {
            getAllFilteredMeals(filterListSelectedItemModel)
            return
        }

        val existedFilteredMealList = _mealsDataOrException.value.data

        _mealsDataOrException.value = DataOrException(status = DataOrExceptionStatus.Loading)

        val filteredMealModelList = mutableListOf<FilteredMealModel>()

        if (name.trim().isNotEmpty() && FilterListSelectedItemHelper.isNotEmpty(filterListSelectedItemModel = filterListSelectedItemModel)) {
            existedFilteredMealList?.forEach { existedFilteredMeal ->
                if (existedFilteredMeal.strMeal.contains(name.trim())) {
                    filteredMealModelList.add(existedFilteredMeal)
                }
            }
        }
        else {
            val dataOrException = mealServiceRepository.getMealByName(name = name.trim())
            if (dataOrException.exception != null) {
                _mealsDataOrException.value = DataOrException(
                    exception = dataOrException.exception,
                    status = DataOrExceptionStatus.Failure
                )
                return
            }

            dataOrException.data?.meals?.forEach {
                filteredMealModelList.add(FilteredMealModel(
                    idMeal = it.idMeal,
                    strMeal = it.strMeal,
                    strMealThumb = it.strMealThumb
                ))
            }
        }

        _mealsDataOrException.value = DataOrException(
            data = (filteredMealModelList).toMutableList(),
            status = DataOrExceptionStatus.Success
        )
    }

    private suspend fun getAllFilteredMeals(filterListSelectedItemModel: FilterListSelectedItemModel): Unit {
        _mealsDataOrException.value = DataOrException(status = DataOrExceptionStatus.Loading)

        var filteredMealModel: MutableList<FilteredMealModel> = mutableListOf()

        val mealsByCategory = getSingleFilteredMeals<MutableList<MealListByCategoryServiceModel.Meal>>(
            filterType = FilterType.Category,
            filteredMealModelList = filteredMealModel,
            filterListSelectedItemModel = filterListSelectedItemModel
        )
        if (mealsByCategory.status == DataOrExceptionStatus.Failure) { return }
        filteredMealModel = mealsByCategory.data!!

        val mealsByIngredients = getSingleFilteredMeals<MutableList<MealListByIngredientServiceModel.Meal>>(
            filteredMealModelList = filteredMealModel,
            filterType = FilterType.Ingredient,
            filterListSelectedItemModel = filterListSelectedItemModel
        )
        if (mealsByIngredients.status == DataOrExceptionStatus.Failure) { return }
        filteredMealModel = mealsByIngredients.data!!

        val mealsByArea = getSingleFilteredMeals<MutableList<MealListByAreaServiceModel.Meal>>(
            filteredMealModelList = filteredMealModel,
            filterType = FilterType.Area,
            filterListSelectedItemModel = filterListSelectedItemModel
        )
        if (mealsByArea.status == DataOrExceptionStatus.Failure) { return }
        filteredMealModel = mealsByArea.data!!


        filteredMealModel = checkDataMealsWithMarkedMeals(filteredMealModel)
        filteredMealModel = checkDataMealsWithLikedMeals(filteredMealModel)

        _mealsDataOrException.value = DataOrException(
            data = filteredMealModel,
            status = DataOrExceptionStatus.Success
        )
    }

    private suspend fun <T> getSingleFilteredMeals(
        filterType: FilterType,
        filteredMealModelList: MutableList<FilteredMealModel>,
        filterListSelectedItemModel: FilterListSelectedItemModel
    ): DataOrException<MutableList<FilteredMealModel>> {
        val notCheckingPreviousFilteredList = FilterListSelectedItemHelper.notCheckingPreviousFilteredList(
            filterType = filterType,
            filterListSelectedItemModel = filterListSelectedItemModel
        )

        when (filterType) {
            FilterType.Category -> {
                if (filterListSelectedItemModel.category.isNotEmpty()) {
                    val dataOrException =
                        mealServiceRepository.getFilteredMealListByCategory(filterListSelectedItemModel.category)

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

                    val jointMealList = jointFilteredMealsWithDataMeals(
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
                if (filterListSelectedItemModel.ingredients.isNotEmpty()) {
                    var jointMealList = filteredMealModelList

                    filterListSelectedItemModel.ingredients.forEach {
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

                        jointMealList = jointFilteredMealsWithDataMeals(
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
                if (filterListSelectedItemModel.area.isNotEmpty()) {
                    val dataOrException =
                        mealServiceRepository.getFilteredMealListByArea(filterListSelectedItemModel.area)

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

                    val jointMealList = jointFilteredMealsWithDataMeals(
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

    private fun <T> jointFilteredMealsWithDataMeals(
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

    private fun checkDataMealsWithMarkedMeals(
        filteredMealModelList: MutableList<FilteredMealModel>
    ): MutableList<FilteredMealModel> {
        _markedMealList.value.forEach { markedMeal ->
            filteredMealModelList.forEach { filteredMeal ->

                if (markedMeal.idMeal == filteredMeal.idMeal) {
                    filteredMeal.isMarked = true
                    filteredMeal.markColor = markedMeal.markColor
                    filteredMeal.markName = markedMeal.markName
                }

            }
        }

        return filteredMealModelList
    }

    private fun checkDataMealsWithLikedMeals(
        filteredMealModelList: MutableList<FilteredMealModel>
    ): MutableList<FilteredMealModel> {
        _likedMealList.value.forEach { markedMeal ->
            filteredMealModelList.forEach { filteredMeal ->

                if (markedMeal.idMeal == filteredMeal.idMeal) {
                    filteredMeal.isLiked = true
                }

            }
        }

        return filteredMealModelList
    }

}

