package com.metoer.clocktracker.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarm_items")
data class ClockItem(
    @ColumnInfo(name = "time")
    var time: String,
    @ColumnInfo(name = "repeat")
    var date: String,
    @ColumnInfo(name = "ring_sound")
    var ringSound: String,
    @ColumnInfo(name = "tag")
    var tag: String
) {
    @PrimaryKey(autoGenerate = true)
    var ID: Int? = null
}

// Günleri, zilsesi, titret , etiket