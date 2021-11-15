package com.example.kotlin_romdatabase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_romdatabase.sqlite.AppDatabase

class ViewModelFactory(private val appdatabase: AppDatabase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(appdatabase) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }

}