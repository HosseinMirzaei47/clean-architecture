package com.speaking.partner.domain.usecases.task

import com.speaking.partner.domain.baseusecases.UseCase
import com.speaking.partner.domain.coroutineUtils.IoDispatcher
import com.speaking.partner.domain.notification.TaskAlarmManager
import com.speaking.partner.domain.repositories.task.TaskRepository
import com.speaking.partner.model.models.task.TaskWithReminderPendingIntent
import com.support.multicalendar.DandelionDate
import com.support.multicalendar.models.CalendarType
import kotlinx.coroutines.CoroutineDispatcher
import java.util.*
import javax.inject.Inject

class ChangeIsDoneUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskAlarmManager: TaskAlarmManager,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<TaskWithReminderPendingIntent, Unit>(dispatcher) {
    override suspend fun execute(parameters: TaskWithReminderPendingIntent) {
        parameters.task.let { task ->
            if (!task.isDone) {
                task.reminders.forEach { reminder ->
                    if (reminder.hasValidDueTime) {
                        parameters.pendingIntent?.let { pendingIntent ->
                            taskAlarmManager.setAlarmForTask(
                                reminder,
                                pendingIntent
                            )
                        }
                    }
                }
                task.doneDate = null
            } else {
                taskAlarmManager.cancelAlarmForTask(task.id)
                task.doneDate = DandelionDate(TimeZone.getDefault(), CalendarType.GREGORIAN)
            }
            taskRepository.changeIsDoneTask(task.id, task.isDone, task.doneDate?.timeInMillis)
        }
    }
}