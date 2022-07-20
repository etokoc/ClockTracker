package com.metoer.clocktracker.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.metoer.clocktracker.data.db.ClockItem
import com.metoer.clocktracker.data.repositories.ClockRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AlarmSettingsViewModel(
    private val repository: ClockRepository
) : ViewModel() {
    protected val compositeDisposable = CompositeDisposable()
    var alarmList = MutableLiveData<List<ClockItem>>()

    fun updateAdd(clockItem: ClockItem) {
        repository.updateAdd(clockItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {

            }).let {
                compositeDisposable.add(it)
            }
    }

    override fun onCleared() {
        compositeDisposable.clear()
        compositeDisposable.dispose()
        super.onCleared()
    }
}