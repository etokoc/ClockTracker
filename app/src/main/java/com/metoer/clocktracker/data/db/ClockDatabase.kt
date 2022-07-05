package com.metoer.clocktracker.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.metoer.clockacker.data.db.ClockDao

@Database(
    entities = [ClockItem::class],
    version = 1
)
abstract class ClockDatabase : RoomDatabase() {
   // abstract fun getClockDao(): ClockDao

    companion object {
        var database: ClockDatabase? = null
        fun createDatabase(context: Context): ClockDatabase? {
            if (database == null) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    ClockDatabase::class.java, "ClockDatabase.db"
                ).build()
            }
            return database
        }
    }

}