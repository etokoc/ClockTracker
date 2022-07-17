package com.metoer.clocktracker.data.repositories

import com.metoer.clocktracker.data.db.ClockDatabase
import com.metoer.clocktracker.data.db.ClockItem

class ClockRepository(
    private val db: ClockDatabase
) {
     fun updateAdd(item: ClockItem) = db.getClockDao().updateAdd(item)
}