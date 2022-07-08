package com.metoer.clocktracker.other.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.*

class AlarmService(val context: Context) {
    public fun createAlarm() {
        val alarmCalender = Calendar.getInstance()
        val calSet = alarmCalender.clone() as Calendar
        calSet[Calendar.HOUR_OF_DAY] = 23
        calSet[Calendar.MINUTE] = 43
        calSet[Calendar.SECOND] = 0
        calSet[Calendar.MILLISECOND] = 0
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 100, intent, 0)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        alarmManager?.set(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), pendingIntent)

    }
}