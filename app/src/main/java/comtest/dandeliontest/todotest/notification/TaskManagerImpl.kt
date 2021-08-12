package comtest.dandeliontest.todotest.notification

import androidx.work.BackoffPolicy
import androidx.work.ExistingWorkPolicy.REPLACE
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import comtest.dandeliontest.todotest.notification.NotificationWorker.Companion.WORKER_NAME
import comtest.dandeliontest.todotest.update.VersionControlWorker
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TaskManagerImpl @Inject constructor(
    private val workManager: WorkManager
) : TaskManager {
    override fun firstRunNotificationWorker() {
        val request = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(2, TimeUnit.MINUTES)
            .build()
        workManager.enqueueUniqueWork(WORKER_NAME, REPLACE, request)
    }

    override fun scheduleRunNotificationWorker() {
        val currentDate = Calendar.getInstance()
        val dueDate = Calendar.getInstance()
        // Set Execution around 08:00:00 AM
        dueDate.set(Calendar.HOUR_OF_DAY, 8)
        dueDate.set(Calendar.MINUTE, 0)
        dueDate.set(Calendar.SECOND, 0)
        if (dueDate.before(currentDate)) {
            dueDate.add(Calendar.HOUR_OF_DAY, 24)
        }
        val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis
        val dailyWorkRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
            .build()

        workManager.enqueueUniqueWork(WORKER_NAME, REPLACE, dailyWorkRequest)
    }

    override fun checkForUpdates() {
        val request = OneTimeWorkRequestBuilder<VersionControlWorker>()
            .addTag(VersionControlWorker.WORKER_NAME)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                OneTimeWorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()

        workManager.enqueueUniqueWork(VersionControlWorker.WORKER_NAME, REPLACE, request)
    }
}