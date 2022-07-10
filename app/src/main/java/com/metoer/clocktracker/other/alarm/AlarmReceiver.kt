package com.metoer.clocktracker.other.alarm

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.metoer.clocktracker.R
import com.metoer.clocktracker.ui.view.activity.ClockActivity


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
        createNotificationChannel(context)
        createNotification(context)
    }

    private val CHANNEL_ID = "clockID"
    private val CHANNEL_NAME = "channelName"
    private val NOTIFICATION_ID = 61

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel(context: Context?) {

        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            lightColor = Color.GREEN
            enableLights(true)
        }
        val manager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotification(context: Context?) {
        val intent = Intent(context, ClockActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val notification: Notification = NotificationCompat.Builder(context!!, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_alarm)
            .setAutoCancel(true)
            .setContentText("Uyanma Vakti")
            .setContentTitle("Title")
            .setContentIntent(pendingIntent)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ic_alarm))
            .setShowWhen(true)
            .addAction(R.mipmap.ic_launcher, context.getString(R.string.snooze), null)
            .addAction(R.mipmap.ic_launcher, context.getString(R.string.turn_off), null)
            .build()

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

}