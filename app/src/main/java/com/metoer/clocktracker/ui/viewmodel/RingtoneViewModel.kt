package com.metoer.clocktracker.ui.viewmodel

import android.app.Activity
import android.database.Cursor
import android.media.RingtoneManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.metoer.clocktracker.model.RingtoneModel

class RingtoneViewModel : ViewModel() {

    fun loadLocalRingtonesUris(activity: Activity): MutableLiveData<MutableList<RingtoneModel>>? {
        val liveData = MutableLiveData<MutableList<RingtoneModel>>()
        val alarms: MutableList<RingtoneModel> = mutableListOf()
        try {
            val ringtoneMgr = RingtoneManager(activity)
            ringtoneMgr.setType(RingtoneManager.TYPE_RINGTONE)
            val alarmsCursor: Cursor = ringtoneMgr.cursor
            val alarmsCount: Int = alarmsCursor.count
            if (alarmsCount == 0 && !alarmsCursor.moveToFirst()) {
                alarmsCursor.close()
                return null
            }
            while (alarmsCursor.moveToNext()) {
                val currentPosition: Int = alarmsCursor.position
                val ringtoneUri = ringtoneMgr.getRingtoneUri(currentPosition)
                val ringtoneName = ringtoneMgr.getRingtone(currentPosition).getTitle(activity.applicationContext)
                alarms.add(RingtoneModel(ringtoneName, ringtoneUri))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        liveData.value = alarms
        return liveData
    }
}