package com.speaking.partner.domain.notification

import android.app.Notification.VISIBILITY_PUBLIC
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.annotation.RequiresApi
import androidx.annotation.WorkerThread
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import com.speaking.partner.domain.R
import com.speaking.partner.domain.coroutineUtils.ApplicationScope
import com.speaking.partner.domain.usecases.task.GetRawTaskUseCase
import com.speaking.partner.model.models.task.ParcelablePendingIntent
import com.speaking.partner.model.models.task.Task
import com.speaking.partner.shared.resource.onSuccess
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class AlarmBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var getRawTask: GetRawTaskUseCase

    @ApplicationScope
    @Inject
    lateinit var externalScope: CoroutineScope

    override fun onReceive(context: Context, intent: Intent) {
        println("Alarm received")

        val taskId = intent.getLongExtra(EXTRA_TASK_ID, 0)

        val pendingIntentBundle =
            intent.getBundleExtra(EXTRA_TASK_PENDING_INTENT)
        val pendingIntent =
            pendingIntentBundle?.get(EXTRA_TASK_PENDING_INTENT) as ParcelablePendingIntent

        val channel = intent.getStringExtra(EXTRA_NOTIFICATION_CHANNEL)

        if (channel == CHANNEL_ID_UPCOMING) {
            externalScope.launch {
                notifyWithoutUserEvent(taskId.toString(), context, pendingIntent)
            }
        }
    }

    private suspend fun notifyWithoutUserEvent(
        taskId: String,
        context: Context,
        pendingIntent: ParcelablePendingIntent
    ) {
        try {
            // Using coroutineScope to propagate exception to the try/catch block
            coroutineScope {
                // Using async coroutine builder to wait for the result of the use case computation
                val task = withContext(Dispatchers.Default) {
                    getRawTask(taskId.toLong())
                }
                task.onSuccess {
                    if (!it.isDone) {
                        showNotification(context, it, pendingIntent)
                    }
                }
            }
        } catch (ex: Exception) {
            println("Exception loading task for notification: ${ex.message}")
        }
    }

    @WorkerThread
    private fun showNotification(
        context: Context,
        task: Task,
        pendingIntent: ParcelablePendingIntent
    ): Int {
        val notificationId = task.id.hashCode()
        val notificationManager: NotificationManager = context.getSystemService()
            ?: throw Exception("Notification Manager not found.")

        val markAsDonePendingIntent: PendingIntent =
            PendingIntent.getBroadcast(
                context,
                (task.id.toString() + CHANNEL_ID_UPCOMING).hashCode(),
                Intent(
                    context,
                    NotificationActionsReceiver::class.java
                )
                    .putExtra(EXTRA_TASK_ID, task.id)
                    .putExtra(EXTRA_NOTIFICATION_ID, notificationId)
                    .putExtra(
                        EXTRA_NOTIFICATION_CHANNEL,
                        CHANNEL_ID_UPCOMING
                    ),
                FLAG_UPDATE_CURRENT
            )

        if (VERSION.SDK_INT >= VERSION_CODES.O) {
            makeNotificationChannelForTask(context, notificationManager)
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID_UPCOMING)
            .setContentTitle(task.title)
            .setContentText(task.description)
            .setContentIntent(pendingIntent.pendingIntent)
            .setSmallIcon(R.drawable.ic_small_icon)
            .setTimeoutAfter(TimeUnit.MINUTES.toMillis(10)) // Backup (cancelled with receiver)
            .setAutoCancel(true)
            .addAction(R.drawable.ic_small_icon, "Done", markAsDonePendingIntent)
            .build()

        notificationManager.notify(notificationId, notification)
        return notificationId
    }

    @RequiresApi(VERSION_CODES.O)
    private fun makeNotificationChannelForTask(
        context: Context,
        notificationManager: NotificationManager
    ) {
        notificationManager.createNotificationChannel(
            NotificationChannel(
                CHANNEL_ID_UPCOMING,
                context.getString(R.string.task_notifications),
                NotificationManager.IMPORTANCE_HIGH
            ).apply { lockscreenVisibility = VISIBILITY_PUBLIC }
        )
    }

    companion object {
        const val EXTRA_NOTIFICATION_CHANNEL = "notification_channel"
        const val CHANNEL_ID_UPCOMING = "upcoming_channel_id"
        const val EXTRA_TASK_ID = "task_id_extra"
        const val EXTRA_TASK_PENDING_INTENT = "task_pending_intent_extra"
        const val EXTRA_NOTIFICATION_ID = "notification_id"
    }
}