package com.jwdfhi.meal_up.models

import kotlinx.coroutines.flow.MutableStateFlow

data class DataOrException<T, E: Exception, S: DataOrExceptionStatus>(
    var data: T? = null,
    var exception: E? = null,
    var status: DataOrExceptionStatus = DataOrExceptionStatus.Loading
)
