package com.metoer.clocktracker.day

import android.os.Build
import androidx.annotation.RequiresApi
import com.metoer.clocktracker.model.ClockModel
import com.metoer.clocktracker.other.convertToInt
import java.time.LocalDateTime
import java.util.*


class DayController {
    companion object {
        var selectedDay = "0000000"
    }

    fun selectDay(dayEnum: DayStatusEnum, specialDays: ArrayList<Boolean>?): String {
        selectedDay = when (dayEnum) {
            DayStatusEnum.ONEDAY -> {
                "1"
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

    private fun booleanConvertToString(arrayList: ArrayList<Boolean>): String {
        var string = ""
        arrayList.forEachIndexed { index, b ->
            string += b.convertToInt().toString()
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
        val hour = date.substring(0, date.indexOf(':')).toInt()
        val minute = date.substring(date.indexOf(':') + 1, date.length).toInt()
        var dt = Date()
        val c = Calendar.getInstance()
        c.time = dt
        c.add(Calendar.DATE, getMostDay(intDate).toInt())
        dt = c.time
        dt.hours = hour
        dt.minutes = minute
        val secondTime = dt

        val millis = secondTime.time - System.currentTimeMillis()
        val hours: Long = millis / (1000 * 60 * 60)
        val mins: Long = millis / (1000 * 60) % 60
        return "$hours saat $mins dakika kaldı."
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getToday(): ClockModel {
        val today = LocalDateTime.now()
        return ClockModel(today.hour, today.minute)
    }

    fun getMostDay(days: String): String {
        val inDay = Calendar.getInstance().time.day
        var startDay = 0
        var endDate = 0
        days.forEachIndexed { index, c ->
            if (inDay - 1 == index) {
                startDay = index
                for (item in index until days.length) {
                    if (days[item] == '1') {
                        endDate = item
                        break
                    }
                }
            }
        }

        var sonuc = endDate - startDay
        if (sonuc < 0) {
            sonuc += 7
        }

        return sonuc.toString()
    }
}