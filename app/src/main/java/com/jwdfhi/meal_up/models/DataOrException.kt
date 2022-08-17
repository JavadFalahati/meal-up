package com.jwdfhi.meal_up.models

import kotlinx.coroutines.flow.MutableStateFlow

data class DataOrException<T>(
    var data: T? = null,
    var exception: Exception? = null,
    var status: DataOrExceptionStatus = DataOrExceptionStatus.Loading
)
