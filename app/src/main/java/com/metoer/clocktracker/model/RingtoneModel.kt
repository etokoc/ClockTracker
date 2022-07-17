package com.metoer.clocktracker.model

import android.net.Uri

data class RingtoneModel(
    var ringtoneName: String? = "",
    var ringtoneUri: Uri? = null
)