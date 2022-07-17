package com.metoer.clocktracker.day

import com.metoer.clocktracker.other.convertToInt

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
}