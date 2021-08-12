package com.speaking.partner.domain.notification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.getSystemService
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Receives broadcast intents with information to hide notifications.
 */
@AndroidEntryPoint
class CancelNotificationBroadcastReceiver : BroadcastReceiver() {

    companion object {
        const val NOTIFICATION_ID_EXTRA = "notification_id_extra"
    }

    @Inject
    @ApplicationContext
    lateinit var context: Context

    override fun onReceive(context: Context, intent: Intent) {
        val notificationId = intent.getIntExtra(
            NOTIFICATION_ID_EXTRA, 0
        )
        println("Hiding notification for $notificationId")

        val notificationManger: NotificationManager =
            context.getSystemService() ?: throw Exception("Notification Manager not found.")

        notificationManger.cancel(notificationId)
    }
}
