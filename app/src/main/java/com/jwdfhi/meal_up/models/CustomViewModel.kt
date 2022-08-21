package com.jwdfhi.meal_up.models

import androidx.navigation.NavController

interface CustomViewModel<InitStateEntryType> {

    fun initState(entry: InitStateEntryType): Unit

    fun onBackPressed(navController: NavController): Unit

}