package com.metoer.clockacker.data.db

import androidx.room.*
import com.metoer.clocktracker.data.db.ClockItem
import io.reactivex.Completable
import io.reactivex.Single
@Dao
interface ClockDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateAdd(item: ClockItem): Completable

    @Delete
    fun delete(item: ClockItem): Completable

    @Query("SELECT * FROM alarm_items")
    fun getAllClockItems(): Single<List<ClockItem>>
}