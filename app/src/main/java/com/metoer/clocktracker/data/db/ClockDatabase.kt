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
    abstract fun getClockDao(): ClockDao

    companion object {
        private var instance: ClockDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ClockDatabase::class.java, "ShoppingDB.db"
            ).build()
    }

    /*private var instance: ClockDatabase? = null
    private val LOCK = Any()

    operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
        instance ?: createDatabase(context).also { instance = it }
    }

    private fun createDatabase(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            ClockDatabase::class.java, "ShoppingDB.db"
        ).build()*/

   /* var database: ClockDatabase? = null
    fun createDatabase(context: Context): ClockDatabase? {
        if (database == null) {
            database = Room.databaseBuilder(
                context.applicationContext,
                ClockDatabase::class.java, "ClockDatabase.db"
            ).build()
        }
        return database
    }*/

}