package com.dschu.drivermatch.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dschu.drivermatch.viewmodel.DriverMatchVM

class DriverMatchViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DriverMatchVM() as T
    }
}