package com.metoer.clocktracker.other.alarm

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.metoer.clocktracker.R
import com.metoer.clocktracker.other.Constants
import com.metoer.clocktracker.ui.view.activity.ClockActivity
import com.metoer.clocktracker.ui.view.activity.LockScreenActivity


class AlarmReceiver : BroadcastReceiver() {
    private var alarmRingtone: Uri? = null

    companion object {
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
        if (intent?.action == Constants.TURNOFF || intent?.action == Constants.SNOOZE) {
            ringtone.stop()
            alarmButtonControl(context, intent)
        } else {
            playRingtone(context)
            createNotificationChannel(context)
            createNotification(context)
            openWakeLockScreenService(context)
        }
    }

    private fun playRingtone(context: Context?) {
        ringtone = RingtoneManager.getRingtone(context, alarmRingtone)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
            ringtone.isLooping = true
        ringtone.play()
    }

    private fun openWakeLockScreenService(context: Context?) {
        LockScreenOpener.openLockScreen(context!!)
    }

    private fun alarmButtonControl(context: Context?, intent: Intent) {
        NotificationManagerCompat.from(context!!).cancel(Constants.NOTIFICATION_ID)
        when (intent.action) {
            Constants.TURNOFF -> {
            }
            Constants.SNOOZE -> {}
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel(context: Context?) {

        val channel = NotificationChannel(
            Constants.CHANNEL_ID,
            Constants.CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
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
        val fullScreenIntent = Intent(context, LockScreenActivity::class.java)
        val fullScreenPendingIntent = PendingIntent.getActivity(
            context,
            0,
            fullScreenIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val notification = Notification.Builder(context!!, Constants.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_alarm)
            .setAutoCancel(true)
            .setContentText("Uyanma Vakti")
            .setContentTitle("Title")
            .setPriority(Notification.PRIORITY_HIGH)
            .setOngoing(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setContentIntent(pendingIntent)
            .setShowWhen(true)
            .setFullScreenIntent(fullScreenPendingIntent, true)
            .addAction(
                R.mipmap.ic_launcher,
                context.getString(R.string.snooze),
                PendingIntent.getBroadcast(
                    context,
                    101,
                    addIntent(Constants.SNOOZE, context),
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
            .addAction(
                R.mipmap.ic_launcher,
                context.getString(R.string.turn_off),
                PendingIntent.getBroadcast(
                    context,
                    101,
                    addIntent(Constants.TURNOFF, context),
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
            .build()
        with(NotificationManagerCompat.from(context)) {
            notify(Constants.NOTIFICATION_ID, notification)
        }
    }

    fun addIntent(action: String, context: Context?): Intent {
        val createIntent = Intent(context, AlarmReceiver::class.java)
        createIntent.action = action
        return createIntent
    }
}