package com.jwdfhi.meal_up.models

import kotlinx.coroutines.flow.MutableStateFlow

data class DataOrException<T, Boolean, E: Exception, DataOrExceptionStatus>(
    var data: T? = null,
    var loading: Boolean? = null,
    var exception: E? = null,
    var status: DataOrExceptionStatus? = null
)
