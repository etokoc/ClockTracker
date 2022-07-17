package com.metoer.clockacker.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.metoer.clocktracker.data.db.ClockItem
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface ClockDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateAdd(item: ClockItem): Completable

    @Delete
    fun delete(item: ClockItem): Completable

    @Query("SELECT * FROM alarm_items")
    fun getAllClockItems(): Single<List<ClockItem>>
}