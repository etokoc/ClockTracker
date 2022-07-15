package com.metoer.clocktracker.day

import com.metoer.clocktracker.other.convertToInt

class DayController {
    companion object {
        var selectedDay = "0"
    }

    fun selectDay(dayEnum: DayEnum, specialDays: ArrayList<Boolean>?) {
        selectedDay = when (dayEnum) {
            DayEnum.ONEDAY -> {
                "1"
            }
            DayEnum.WEEKDAY -> {
                "1111100"
            }
            DayEnum.EVERYDAY -> {
                "1111111"
            }
            DayEnum.SPECIALDAY -> {
                booleanConvertToInt(specialDays!!)
            }
        }
    }

    private fun booleanConvertToInt(arrayList: ArrayList<Boolean>): String {
        var string = ""
        arrayList.forEachIndexed { index, b ->
            string += b.convertToInt().toString()
        }
        return string
    }
}