package com.metoer.clocktracker.model

data class Alarm(
    var alarmTime: String? = "",
    var alarmAgain: String? = "",
    var remainingTtime: String? = "",
    var alarmChecked: Boolean? = null
)
