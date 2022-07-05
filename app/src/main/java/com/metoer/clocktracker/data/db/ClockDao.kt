package com.metoer.clockacker.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.metoer.clocktracker.data.db.ClockItem

@Dao
interface ClockDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAdd(item: ClockItem)

    @Delete
    suspend fun delete(item: ClockItem)

    @Query("SELECT * FROM alarm_items")
    fun getAllClockItems(): LiveData<List<ClockItem>>
}