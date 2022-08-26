package com.jwdfhi.meal_up.models

import androidx.navigation.NavController

interface CustomViewModel<InitStateFirstEntryType, InitStateSecondEntryType> {

    fun initState(entry: InitStateFirstEntryType): Unit {}
    fun initState(entryOne: InitStateFirstEntryType, entryTwo: InitStateSecondEntryType): Unit {}

    fun onBackPressed(navController: NavController): Unit

}