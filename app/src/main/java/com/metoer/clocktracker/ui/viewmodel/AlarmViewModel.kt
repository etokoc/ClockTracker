package com.metoer.clocktracker.ui.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.metoer.clocktracker.data.db.ClockItem
import com.metoer.clocktracker.data.repositories.ClockRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class AlarmViewModel(
    private val repository: ClockRepository
) : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()
    var personsList = MutableLiveData<List<ClockItem>>()

    fun getAllAlarmData() {
        repository.getAllClockItems()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (!it.isNullOrEmpty()) {
                    personsList.postValue(it)
                } else {
                    personsList.postValue(listOf())
                }
                it?.forEach {

                }
            }, {
            }).let {
                compositeDisposable.add(it)
            }

    }

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }

}