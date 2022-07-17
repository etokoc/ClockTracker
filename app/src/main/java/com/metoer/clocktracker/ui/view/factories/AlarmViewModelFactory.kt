package com.metoer.clocktracker.ui.view.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.metoer.clocktracker.data.repositories.ClockRepository
import com.metoer.clocktracker.ui.viewmodel.AlarmViewModel

class AlarmViewModelFactory(
    private val repository: ClockRepository
):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AlarmViewModel(repository) as T
    }
}