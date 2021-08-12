package comtest.dandeliontest.todotest.domain.notification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.getSystemService
import comtest.dandeliontest.todotest.domain.coroutineUtils.ApplicationScope
import comtest.dandeliontest.todotest.domain.notification.AlarmBroadcastReceiver.Companion.EXTRA_NOTIFICATION_ID
import comtest.dandeliontest.todotest.domain.notification.AlarmBroadcastReceiver.Companion.EXTRA_TASK_ID
import comtest.dandeliontest.todotest.domain.usecases.task.MarkTaskAsDoneUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotificationActionsReceiver : BroadcastReceiver() {

    @Inject
    lateinit var markTaskAsDone: MarkTaskAsDoneUseCase

    @ApplicationScope
    @Inject
    lateinit var externalScope: CoroutineScope

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        val taskId = intent.getLongExtra(EXTRA_TASK_ID, 0)
        externalScope.launch { markTaskAsDone(taskId) }

        val notificationId = intent.getIntExtra(EXTRA_NOTIFICATION_ID, 0)
        val notificationManger: NotificationManager =
            context.getSystemService() ?: throw Exception("Notification Manager not found.")
        notificationManger.cancel(notificationId)
    }
}