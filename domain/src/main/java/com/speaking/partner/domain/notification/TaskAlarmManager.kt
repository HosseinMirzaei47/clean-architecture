package com.speaking.partner.domain.notification

import android.app.AlarmManager
import android.app.AlarmManager.RTC
import android.app.AlarmManager.RTC_WAKEUP
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.AlarmManagerCompat
import androidx.core.content.getSystemService
import com.speaking.partner.model.models.task.ParcelablePendingIntent
import com.speaking.partner.model.models.task.Reminder
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

open class TaskAlarmManager @Inject constructor(@ApplicationContext val context: Context) {

    private val systemAlarmManager: AlarmManager? = context.getSystemService()

    fun setAlarmForTask(reminder: Reminder, pendingIntent: ParcelablePendingIntent) {
        val upcomingIntent =
            makePendingIntent(reminder.taskId, pendingIntent)
        upcomingIntent?.let {
            scheduleAlarmForTask(it, reminder)
        }
    }

    open fun cancelAlarmForTask(taskId: Long) {
        val upcomingIntent =
            makePendingIntent(taskId)
        upcomingIntent?.let {
            cancelAlarmFor(it)
            println("Cancelled upcoming alarm for task $taskId")
        }
    }

    private fun makePendingIntent(
        taskId: Long,
        pendingIntent: ParcelablePendingIntent? = null
    ): PendingIntent? {
        val pendingIntentBundle = Bundle()
        pendingIntentBundle.putParcelable(
            AlarmBroadcastReceiver.EXTRA_TASK_PENDING_INTENT,
            pendingIntent
        )
        return PendingIntent.getBroadcast(
            context,
            (taskId.toString() + AlarmBroadcastReceiver.CHANNEL_ID_UPCOMING).hashCode(),
            Intent(context, AlarmBroadcastReceiver::class.java)
                .putExtra(AlarmBroadcastReceiver.EXTRA_TASK_ID, taskId)
                .putExtra(AlarmBroadcastReceiver.EXTRA_TASK_PENDING_INTENT, pendingIntentBundle)
                .putExtra(
                    AlarmBroadcastReceiver.EXTRA_NOTIFICATION_CHANNEL,
                    AlarmBroadcastReceiver.CHANNEL_ID_UPCOMING
                ),
            FLAG_UPDATE_CURRENT
        )
    }

    private fun cancelAlarmFor(pendingIntent: PendingIntent) {
        try {
            systemAlarmManager?.cancel(pendingIntent)
        } catch (ex: Exception) {
            println("Couldn't cancel alarm for task")
        }
    }

    private fun scheduleAlarmForTask(pendingIntent: PendingIntent, reminder: Reminder) {
        scheduleAlarmFor(pendingIntent, reminder, reminder.time.timeInMillis)
    }

    private fun scheduleAlarmFor(
        pendingIntent: PendingIntent,
        reminder: Reminder,
        triggerAtMillis: Long
    ) {
        systemAlarmManager?.let {
            AlarmManagerCompat.setExactAndAllowWhileIdle(
                systemAlarmManager,
                RTC_WAKEUP,
                triggerAtMillis,
                pendingIntent
            )
            println(
                "Scheduled alarm for task ${reminder.taskId} at $triggerAtMillis for channel: ${AlarmBroadcastReceiver.CHANNEL_ID_UPCOMING}"
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun dismissNotificationInFiveMinutes(notificationId: Int) {
        systemAlarmManager?.let {
            val intent = Intent(context, CancelNotificationBroadcastReceiver::class.java)
            intent.putExtra(
                CancelNotificationBroadcastReceiver.NOTIFICATION_ID_EXTRA, notificationId
            )
            val pendingIntent = PendingIntent.getBroadcast(context, notificationId, intent, 0)
            // TODO: 2/11/2021 change time factor
            val triggerAtMillis =
                System.currentTimeMillis() + 12600000 + 120000 /*Instant.now().plus(5, ChronoUnit.MINUTES).toEpochMilli()*/
            AlarmManagerCompat.setExactAndAllowWhileIdle(
                systemAlarmManager,
                RTC,
                triggerAtMillis,
                pendingIntent
            )
            println("Scheduled notification dismissal for $notificationId at $triggerAtMillis")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun dismissNotification(notificationId: Int) {
        systemAlarmManager?.let {
            val intent = Intent(context, CancelNotificationBroadcastReceiver::class.java)
            intent.putExtra(
                CancelNotificationBroadcastReceiver.NOTIFICATION_ID_EXTRA,
                notificationId
            )
            val pendingIntent = PendingIntent.getBroadcast(context, notificationId, intent, 0)
            AlarmManagerCompat.setExactAndAllowWhileIdle(
                systemAlarmManager,
                RTC,
                System.currentTimeMillis(),
                pendingIntent
            )
            println("Scheduled notification dismissal for $notificationId")
        }
    }
}