package com.speaking.partner.notification

import android.content.Context
import android.os.Bundle
import androidx.hilt.work.HiltWorker
import androidx.navigation.NavDeepLinkBuilder
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.speaking.partner.R
import com.speaking.partner.domain.notification.TaskAlarmManager
import com.speaking.partner.domain.usecases.task.GetFutureRemindersUseCase
import com.speaking.partner.model.models.task.ParcelablePendingIntent
import com.speaking.partner.shared.resource.Resource
import com.speaking.partner.ui.utils.ArgumentKeysConstants.PENDING_INTENT_TASK_ARGUMENT
import com.speaking.partner.ui.utils.hasValidDueTime
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.*

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    private val getUpcomingRemindersUseCase: GetFutureRemindersUseCase,
    private val taskAlarmManager: TaskAlarmManager,
    private val taskManager: TaskManager,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val currentDate = Calendar.getInstance()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 9)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        if (calendar.before(currentDate)) {
            calendar.add(Calendar.HOUR_OF_DAY, 24)
        }
        val remindersResult = getUpcomingRemindersUseCase(calendar.timeInMillis)
        if (remindersResult is Resource.Success) {
            remindersResult.data.forEach { reminder ->
                if (reminder.time.hasValidDueTime()) {
                    val args = Bundle().also {
                        it.putLong(PENDING_INTENT_TASK_ARGUMENT, reminder.taskId)
                    }
                    val pendingIntent = NavDeepLinkBuilder(context)
                        .setGraph(R.navigation.main_graph)
                        .setDestination(R.id.addTaskFragment)
                        .setArguments(args)
                        .createPendingIntent()

                    taskAlarmManager.setAlarmForTask(
                        reminder,
                        ParcelablePendingIntent(pendingIntent)
                    )
                }
            }
        }
        taskManager.scheduleRunNotificationWorker()
        return Result.success()
    }

    companion object {
        const val WORKER_NAME = "reminders_worker"
    }
}