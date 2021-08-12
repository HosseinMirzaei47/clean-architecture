package com.speaking.partner.domain.usecases.task

import com.speaking.partner.domain.baseusecases.UseCase
import com.speaking.partner.domain.coroutineUtils.IoDispatcher
import com.speaking.partner.domain.notification.TaskAlarmManager
import com.speaking.partner.domain.repositories.task.TaskRepository
import com.speaking.partner.model.models.task.IdWithPendingIntent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class UpdateAlarmUseCase @Inject constructor(
    private val taskAlarmManager: TaskAlarmManager,
    private val taskRepository: TaskRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher,
) : UseCase<IdWithPendingIntent, Unit>(dispatcher) {
    override suspend fun execute(parameters: IdWithPendingIntent) {
        taskAlarmManager.cancelAlarmForTask(parameters.taskId)
        taskRepository.getRemindersOfTask(parameters.taskId).forEach { reminderEntity ->
            parameters.pendingIntent?.let { pendingIntent ->
                if (reminderEntity.hasValidDueTime) {
                    taskAlarmManager.setAlarmForTask(
                        reminder = reminderEntity, pendingIntent = pendingIntent
                    )
                }
            }
        }
    }
}