package com.jwdfhi.meal_up.screens.filter

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jwdfhi.meal_up.models.*
import com.jwdfhi.meal_up.repositories.MealServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    public val context: Context,
    private val mealServiceRepository: MealServiceRepository
) : ViewModel() {

    init { initState() }

    private val _initStateDataOrException =
        MutableStateFlow<DataOrException<FilterListItemModel, Exception, DataOrExceptionStatus>>(
            DataOrException(status = DataOrExceptionStatus.Loading)
        )
    val initStateDataOrException = _initStateDataOrException.asStateFlow()
    fun initState(/*TODO: get the default object from ui if its exits*/): Unit {
        viewModelScope.launch(Dispatchers.IO) {
            getCategories()
            if (_categoriesDataOrException.value.status == DataOrExceptionStatus.Failure) {
                _initStateDataOrException.value.status = DataOrExceptionStatus.Failure
                return@launch
            }

            getIngredients()
            if (_ingredientsDataOrException.value.status == DataOrExceptionStatus.Failure) {
                _initStateDataOrException.value.status = DataOrExceptionStatus.Failure
                return@launch
            }

            getAreas()
            if (_areasDataOrException.value.status == DataOrExceptionStatus.Failure) {
                _initStateDataOrException.value.status = DataOrExceptionStatus.Failure
                return@launch
            }

            setInitStateValue()
        }
    }

    private val _categoriesDataOrException =
        MutableStateFlow<DataOrException<MutableList<MealCategoryListServiceModel.Category>, Exception, DataOrExceptionStatus>>(
            DataOrException(status = DataOrExceptionStatus.Loading)
        )
    private suspend fun getCategories(): Unit {
        val dataOrException = mealServiceRepository.getMealCategoryList()
        val exception: Exception? = dataOrException.exception

        if (exception != null) {
            _categoriesDataOrException.value = DataOrException(
                exception = exception,
                status = DataOrExceptionStatus.Failure
            )
            return
        }

        if (dataOrException.data == null || dataOrException.data?.categories == null) {
            _categoriesDataOrException.value = DataOrException(
                status = DataOrExceptionStatus.Failure
            )
            return
        }

        _categoriesDataOrException.value = DataOrException(
            status = DataOrExceptionStatus.Success,
            data = dataOrException.data?.categories?.toMutableList()
        )
    }

    private val _ingredientsDataOrException =
        MutableStateFlow<DataOrException<MutableList<MealIngredientListServiceModel.Meal>, Exception, DataOrExceptionStatus>>(
            DataOrException(status = DataOrExceptionStatus.Loading)
        )
    private suspend fun getIngredients(): Unit {
        val dataOrException = mealServiceRepository.getMealIngredientList()
        val exception: Exception? = dataOrException.exception

        if (exception != null) {
            _ingredientsDataOrException.value = DataOrException(
                exception = exception,
                status = DataOrExceptionStatus.Failure
            )
            return
        }

        if (dataOrException.data == null || dataOrException.data?.meals == null) {
            _ingredientsDataOrException.value = DataOrException(
                status = DataOrExceptionStatus.Failure
            )
            return
        }

        _ingredientsDataOrException.value = DataOrException(
            status = DataOrExceptionStatus.Success,
            data = dataOrException.data?.meals?.toMutableList()
        )
    }

    private val _areasDataOrException =
        MutableStateFlow<DataOrException<MutableList<MealAreaListServiceModel.Meal>, Exception, DataOrExceptionStatus>>(
            DataOrException(status = DataOrExceptionStatus.Loading)
        )
    private suspend fun getAreas() {
        val dataOrException = mealServiceRepository.getMealAreaList()
        val exception: Exception? = dataOrException.exception

        if (exception != null) {
            _areasDataOrException.value = DataOrException(
                exception = exception,
                status = DataOrExceptionStatus.Failure
            )
            return
        }

        if (dataOrException.data == null || dataOrException.data?.meals == null) {
            _areasDataOrException.value = DataOrException(
                status = DataOrExceptionStatus.Failure
            )
            return
        }

        _areasDataOrException.value = DataOrException(
            status = DataOrExceptionStatus.Success,
            data = dataOrException.data?.meals?.toMutableList()
        )
    }

    public fun clearScreenState() {
        _categoriesDataOrException.value.data?.forEach { it.isSelected = false }
        _ingredientsDataOrException.value.data?.forEach { it.isSelected = false }
        _areasDataOrException.value.data?.forEach { it.isSelected = false }

        setInitStateValue()
    }

    public fun clearAllStateOfFilter(filterType: FilterType) {
        when (filterType) {
            FilterType.Category -> _categoriesDataOrException.value.data?.forEach { it.isSelected = false }
            FilterType.Ingredient -> _ingredientsDataOrException.value.data?.forEach { it.isSelected = false }
            FilterType.Area -> _areasDataOrException.value.data?.forEach { it.isSelected = false }
        }

        setInitStateValue()
    }

    public fun <T> clearSingleStateOfFilter(state: T) {
        when (state) {
            is MealCategoryListServiceModel.Category ->
                _categoriesDataOrException.value.data?.find { it == state }?.let { it.isSelected = false }
            is MealIngredientListServiceModel.Meal ->
                _ingredientsDataOrException.value.data?.find { it == state }?.let { it.isSelected = false }
            is MealAreaListServiceModel.Meal ->
                _areasDataOrException.value.data?.find { it == state }?.let { it.isSelected = false }
            else -> throw Exception()
        }

        setInitStateValue()
    }

    public fun <T> selectSingleStateOfFilter(state: T) {
        when (state) {
            is MealCategoryListServiceModel.Category -> {
                _categoriesDataOrException.value.data?.forEach {
                    if (it.isSelected) {
                        it.isSelected = false
                        return@forEach
                    }
                }

                _categoriesDataOrException.value.data?.find { it == state }?.let { it.isSelected = true }
            }
            is MealIngredientListServiceModel.Meal ->
                _ingredientsDataOrException.value.data?.find { it == state }?.let { it.isSelected = true }
            is MealAreaListServiceModel.Meal ->
                _areasDataOrException.value.data?.find { it == state }?.let { it.isSelected = true }
            else -> throw Exception()
        }

        setInitStateValue()
    }

    private fun setInitStateValue() {
        _initStateDataOrException.value = DataOrException(
            status = DataOrExceptionStatus.Success,
            data = FilterListItemModel(
                categories = _categoriesDataOrException.value.data!!.toMutableStateList(),
                ingredients = _ingredientsDataOrException.value.data!!.toMutableStateList(),
                areas = _areasDataOrException.value.data!!.toMutableStateList()
            ),
        )
    }

    public fun submitFilter() {/*TODO: Submit filter*/}

}