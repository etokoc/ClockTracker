package com.metoer.clocktracker.other.alarm

import android.content.Context
import android.os.PowerManager

abstract class LockScreenOpener{
    companion object{
         private var wakeLock :PowerManager.WakeLock? = null
        fun openLockScreen(context: Context){
            val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
            wakeLock = pm.newWakeLock(
                PowerManager.FULL_WAKE_LOCK or
                        PowerManager.ACQUIRE_CAUSES_WAKEUP or
                        PowerManager.ON_AFTER_RELEASE, "WakeLock"
            )
            wakeLock?.acquire(1*60*1000L) //bu süre 1 dakika sonra ekran uyandırmayı serbest bırakmak için böyle yazıldı

        }
        fun releaseLockScreen() {
            if (wakeLock != null) wakeLock?.release()
            wakeLock = null
        }
    }
}