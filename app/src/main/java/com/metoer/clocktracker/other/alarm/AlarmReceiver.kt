package com.metoer.clocktracker.other.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.Ringtone
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
    companion object{
        private lateinit var ringtone: Ringtone
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
//        alarmRingtone = intent?.getStringExtra("ringtone")?.toUri()
        if (alarmRingtone == null)
            alarmRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        if (alarmRingtone == null) {
            alarmRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        }
        if (intent?.action == TURNOFF || intent?.action == SNOOZE) {
            ringtone.stop()
            alarmButtonControl(context, intent)
        } else {
            ringtone = RingtoneManager.getRingtone(context, alarmRingtone)
            ringtone.play()
            createNotificationChannel(context)
            createNotification(context)
        }
    }

    private fun alarmButtonControl(context: Context?, intent: Intent) {
        NotificationManagerCompat.from(context!!).cancel(NOTIFICATION_ID)
        when (intent.action) {
            TURNOFF -> {
            }
            SNOOZE -> {}
        }
    }

    private val CHANNEL_ID = "clockID"
    private val SNOOZE = "SNOOZE"
    private val TURNOFF = "TURNOFF"
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

        val notification = NotificationCompat.Builder(context!!, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_alarm)
            .setAutoCancel(true)
            .setContentText("Uyanma Vakti")
            .setContentTitle("Title")
            .setContentIntent(pendingIntent)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ic_alarm))
            .setShowWhen(true)
            .setFullScreenIntent(
                PendingIntent.getActivity(
                    context,
                    102,
                    Intent(context, ClockActivity::class.java),
                    PendingIntent.FLAG_UPDATE_CURRENT
                ), true
            )
            .addAction(
                R.mipmap.ic_launcher,
                context.getString(R.string.snooze),
                PendingIntent.getBroadcast(
                    context,
                    101,
                    addIntent(SNOOZE, context),
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
            .addAction(
                R.mipmap.ic_launcher,
                context.getString(R.string.turn_off),
                PendingIntent.getBroadcast(
                    context,
                    101,
                    addIntent(TURNOFF, context),
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
            .build()
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    fun addIntent(action: String, context: Context?): Intent {
        val createIntent = Intent(context, AlarmReceiver::class.java)
        createIntent.action = action
        return createIntent
    }
}