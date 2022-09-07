package com.jwdfhi.meal_up.models

import androidx.navigation.NavController

interface CustomViewModel<InitStateFirstEntryType, InitStateSecondEntryType, InitStateThirdEntryType> {

    fun initState(): Unit {}
    fun initState(entry: InitStateFirstEntryType): Unit {}
    fun initState(entryOne: InitStateFirstEntryType, entryTwo: InitStateSecondEntryType): Unit {}
    fun initState(entryOne: InitStateFirstEntryType, entryTwo: InitStateSecondEntryType, entryThree: InitStateThirdEntryType): Unit {}

    fun onBackPressed(navController: NavController): Unit {
        navController.popBackStack()
    }

}