package com.metoer.clocktracker.other.alarm

import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService


class AlarmReceiver : BroadcastReceiver() {
    private var alarmRingtone: Uri? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
//        alarmRingtone = intent?.getStringExtra("ringtone")?.toUri()
        if (alarmRingtone == null)
            alarmRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        if (alarmRingtone == null) {
            alarmRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        }
        val ringtone = RingtoneManager.getRingtone(context, alarmRingtone)
        ringtone.play()
        createNotification(context)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotification(context: Context?) {
//        val CHANNEL_ID = "mobilhanem";
//        val CHANNEL_NAME = "Mobilhanem Dersleri"
//        val channel = NotificationChannel(
//            CHANNEL_ID,
//            CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
//        );
//        channel.enableVibration(true);
//        val manager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        val NOTIFICATION_ID = 52 //Notification id si, channel ile ilgisi bulunmuyor
//
//        val notification: Notification =Notification.Builder(context, CHANNEL_ID)
//            .setContentTitle("Mobilhanem Notification Channel")
//            .setContentText("Example Text")
//            .setSmallIcon(R.drawable.btn_star)
//            .setAutoCancel(true)
//            .build()
//        manager!!.notify(NOTIFICATION_ID, notification)
    }
}