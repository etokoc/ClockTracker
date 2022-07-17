package com.metoer.clocktracker.day

import android.os.Build
import androidx.annotation.RequiresApi
import com.metoer.clocktracker.model.ClockModel
import com.metoer.clocktracker.other.convertToInt
import java.time.LocalDateTime

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
    fun remaining(date: String): String {
        var hour = date.substring(0, date.indexOf(':')).toInt()
        var minute = date.substring(date.indexOf(':') + 1, date.length).toInt()
        val today = getToday()
        var remainHour = hour - today.hour
        var remainMinute = minute - today.minute

        return "$remainHour saat $remainMinute dakika kaldı."
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getToday(): ClockModel {
        val today = LocalDateTime.now()
        return ClockModel(today.hour, today.minute)
    }
}