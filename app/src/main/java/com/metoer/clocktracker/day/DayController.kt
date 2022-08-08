package com.metoer.clocktracker.day

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.metoer.clocktracker.model.ClockModel
import com.metoer.clocktracker.other.convertToInt
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*


class DayController {
    companion object {
        var selectedDay = "0000000"
    }

    fun selectDay(dayEnum: DayStatusEnum, specialDays: ArrayList<Boolean>?): String {
        selectedDay = when (dayEnum) {
            DayStatusEnum.ONEDAY -> {
                booleanConvertToString(specialDays!!, '2')
            }
            DayStatusEnum.WEEKDAY -> {
                "1111100"
            }
            DayStatusEnum.EVERYDAY -> {
                "1111111"
            }
            DayStatusEnum.SPECIALDAY -> {
                booleanConvertToString(specialDays!!)
            }
        }
        return selectedDay
    }

    private fun booleanConvertToString(
        arrayList: ArrayList<Boolean>,
        itemType: Char = '1'
    ): String {
        var item = 0
        var string = ""
        arrayList.forEachIndexed { index, b ->
            item = b.convertToInt()
            if (item != 0)
                item = itemType.toString().toInt()
            string += item
        }
        return string
    }

    fun getDayString(days: String): String {
        var selectDaysString = ""
        when {
            days.toInt() == 1111100 -> {
                selectDaysString = "Hafta içi"
            }
            days.toInt() == 1111111 -> {
                selectDaysString = "Her gün"
            }
            days == "0000011" -> {
                selectDaysString = "Hafta sonu"
            }
            days.contains('2') -> {
                selectDaysString = "Bir kez"
            }
            else -> {
                days.forEachIndexed { index, c ->
                    if (c == '1') {
                        selectDaysString += DayStringEnum.values()[index].toString() + " "
                    }
                }
            }
        }
        return selectDaysString
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun remaining(date: String, intDate: String): String {
        val alarmTime = alarmTime(date)
        val hour = alarmTime.hour
        val minute = alarmTime.minute
        var dt = Date()
        val c = Calendar.getInstance()
        c.time = dt
        c.add(Calendar.DATE, getMostDay(intDate, date).toInt())
        dt = c.time
        dt.hours = hour
        dt.minutes = minute
        val secondTime = dt
        val millis = secondTime.time - System.currentTimeMillis()
        val days: Long = millis / (1000 * 60 * 60 * 24)
        val hours: Long = millis / (1000 * 60 * 60) % 24
        val mins: Long = millis / (1000 * 60) % 60
        return whenAlarmMessage(days, hours, mins)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMostDay(days: String, date: String): String {
        val inDay = (Calendar.getInstance().time.day) - 1
        var startDay = 0
        var nextDate = 0
        val format = SimpleDateFormat("hh:mm")
        val alarmTime: Date =
            format.parse("${alarmTime(date).hour}:${alarmTime(date).minute}") as Date
        val currentTime: Date = format.parse("${getToday().hour}:${getToday().minute}") as Date
        val mills = alarmTime.time - currentTime.time
        days.forEachIndexed { index, c ->
            if (inDay == index) {
                startDay = index
                for (item in days.indices) {
                    if (inDay == item && mills > 0 && days[item] == '2') {
                        Log.i("MYTAG", "bugün kurulu: $item. cı alarm")
                    }
                }
            }
        }

        var sonuc = nextDate - startDay
        if (sonuc < 0) {
            sonuc += 7
        }
        return sonuc.toString()
    }

    fun alarmTime(date: String): ClockModel {
        val hour = date.substring(0, date.indexOf(':')).toInt()
        val minute = date.substring(date.indexOf(':') + 1, date.length).toInt()
        return ClockModel(hour, minute)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getToday(): ClockModel {
        val today = LocalDateTime.now()
        return ClockModel(today.hour, today.minute)
    }

    private fun whenAlarmMessage(days: Long, hours: Long, mins: Long): String {
        var message = ""

        if (days != 0L) {
            message += "$days gün "
        }
        if (hours != 0L) {
            message += "$hours saat "
        }
        if (mins != 0L) {
            message += "$mins dakika "
        }

        return "Alarm " + message + "sonra çalacak"
    }


}