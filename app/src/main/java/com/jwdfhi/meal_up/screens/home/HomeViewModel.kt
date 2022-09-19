package com.jwdfhi.meal_up.screens.home

import android.content.Context
import android.os.CountDownTimer
import android.widget.Toast
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.jwdfhi.meal_up.R
import com.jwdfhi.meal_up.models.*
import com.jwdfhi.meal_up.repositories.MealDatabaseRepository
import com.jwdfhi.meal_up.repositories.MealServiceRepository
import com.jwdfhi.meal_up.utils.isNotEmpty
import com.jwdfhi.meal_up.utils.notCheckingPreviousFilteredList
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
) : ViewModel(), CustomViewModel<FilterListSelectedItemModel, Boolean, String> {

    override fun initState(
        filterListSelectedItemModel: FilterListSelectedItemModel,
        refresh: Boolean,
        searchValue: String
    ) {
        this.filterListSelectedItemModel = filterListSelectedItemModel

        viewModelScope.launch(Dispatchers.IO) {

            when (refresh) {
                false -> {
                    _mealsDataOrException.value.data = checkDataMealsWithStoreMeals(_mealsDataOrException.value.data!!)
                }
                true -> {
                    getStoredMeals()

                    if (filterListSelectedItemModel.isNotEmpty()) {
                        getAllFilteredMeals(filterListSelectedItemModel)
                    }
                    else { getRandomMeals() }

                    if (searchValue.isNotEmpty()) {
                        searchMealByName(
                            filterListSelectedItemModel,
                            searchValue
                        )
                    }
                }
            }

        }

    }

    override fun onBackPressed(navController: NavController) {
        TODO("Not yet implemented")
    }

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

    private var _storedMealList = listOf<MealModel>()

    var filterListSelectedItemModel = FilterListSelectedItemModel()

    private fun getStoredMeals() {
        viewModelScope.launch(Dispatchers.IO) {
            _storedMealList = mealDatabaseRepository.getMeals()

            removeRedundantMealFromDatabaseIfIsNotLikedOrMarked(storedMeals = _storedMealList)
        }
    }

    private suspend fun removeRedundantMealFromDatabaseIfIsNotLikedOrMarked(
        storedMeals: List<MealModel>
    ) {
        storedMeals.forEach { meal ->
            if (!meal.isLiked && !meal.isMarked) {
                mealDatabaseRepository.deleteMeal(meal.idMeal)
            }
        }
    }

    private suspend fun getRandomMeals(): Unit {
        _mealsDataOrException.value = DataOrException(status = DataOrExceptionStatus.Loading)

        // if (!deviceHaveConnection(context)) {
        //     _mealsDataOrException.value = DataOrException(
        //         exception = Exception(/*message = "Can't Connect to the Server!"*/),
        //         status = DataOrExceptionStatus.Failure
        //     )
        //     return
        // }

        var mealList = mutableListOf<FilteredMealModel>()

        var repeatCount: Int = 7
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
            data = checkDataMealsWithStoreMeals(mealList),
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
                strMealThumb = it.strMealThumb,
            ))
        }

        return DataOrException(
            data = filteredMealModel,
            status = DataOrExceptionStatus.Success
        )
    }

    suspend fun searchMealByName(
        filterListSelectedItemModel: FilterListSelectedItemModel,
        name: String
    ): Unit {
        if (name.trim().isEmpty() && !filterListSelectedItemModel.isNotEmpty()) {
            getRandomMeals()
            return
        }
        if (name.trim().isEmpty() && filterListSelectedItemModel.isNotEmpty()) {
            getAllFilteredMeals(filterListSelectedItemModel)
            return
        }

        val existedFilteredMealList = _mealsDataOrException.value.data

        _mealsDataOrException.value = DataOrException(status = DataOrExceptionStatus.Loading)

        val filteredMealModelList = mutableListOf<FilteredMealModel>()

        if (name.trim().isNotEmpty() && filterListSelectedItemModel.isNotEmpty()) {
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
            data = checkDataMealsWithStoreMeals((filteredMealModelList).toMutableList()),
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


        _mealsDataOrException.value = DataOrException(
            data = checkDataMealsWithStoreMeals(filteredMealModel),
            status = DataOrExceptionStatus.Success
        )
    }

    private suspend fun <T> getSingleFilteredMeals(
        filterType: FilterType,
        filteredMealModelList: MutableList<FilteredMealModel>,
        filterListSelectedItemModel: FilterListSelectedItemModel
    ): DataOrException<MutableList<FilteredMealModel>> {
        val notCheckingPreviousFilteredList = filterListSelectedItemModel.notCheckingPreviousFilteredList(
            filterType = filterType,
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

    private fun checkDataMealsWithStoreMeals(
        filteredMealModelList: MutableList<FilteredMealModel>
    ): MutableList<FilteredMealModel> {
        _storedMealList.forEach { storedMeal ->
            filteredMealModelList.forEach { filteredMeal ->

                if (storedMeal.idMeal == filteredMeal.idMeal) {
                    filteredMeal.isLiked = storedMeal.isLiked

                    filteredMeal.isMarked = storedMeal.isMarked
                    filteredMeal.markCategory = storedMeal.markCategory
                }

            }
        }

        return filteredMealModelList
    }


    public fun likeOrUnLikeMeal(filteredMealModel: FilteredMealModel) {
        _mealsDataOrException.value.status = DataOrExceptionStatus.Loading

        if (_mealsDataOrException.value.data == null) { return }

        val meaIndex: Int = _mealsDataOrException.value.data!!.indexOf(filteredMealModel)
        val updatedMealModel = filteredMealModel
        updatedMealModel.isLiked = !updatedMealModel.isLiked

        checkMealIsExistThenUpsert(updatedMealModel).let {
            _mealsDataOrException.value.data!![meaIndex] = updatedMealModel

            _mealsDataOrException.value = DataOrException(
                data = _mealsDataOrException.value.data,
                status = DataOrExceptionStatus.Success
            )
        }
    }

    private fun checkMealIsExistThenUpsert(updatedMealModel: FilteredMealModel): Unit {
        viewModelScope.launch(Dispatchers.IO) {
            when (getMealFromDatabase(updatedMealModel.idMeal) != null) {
                false -> insertMealOnDatabase(updatedMealModel)
                true -> updateMealOnDatabase(updatedMealModel)
            }
        }
    }

    private suspend fun insertMealOnDatabase(filteredMealModel: FilteredMealModel) {
        val networkMealDataOrException = getMealFromNetwork(filteredMealModel.idMeal)
        if (networkMealDataOrException.status == DataOrExceptionStatus.Failure) {
            Toast.makeText(context, R.string.An_unexpected_error_accrued, Toast.LENGTH_LONG).show()
            return
        }

        networkMealDataOrException.data!!.isLiked = filteredMealModel.isLiked

        mealDatabaseRepository.insertMeal(networkMealDataOrException.data!!)
    }

    private suspend fun updateMealOnDatabase(filteredMealModel: FilteredMealModel) {
        val storedMeal = getMealFromDatabase(filteredMealModel.idMeal)

        storedMeal!!.isLiked = filteredMealModel.isLiked

        mealDatabaseRepository.updateMeal(storedMeal)
    }

    private suspend fun getMealFromDatabase(id: String): MealModel? = mealDatabaseRepository.getMealById(id)

    private suspend fun getMealFromNetwork(id: String) = mealServiceRepository.getMealDetail(id)

}

