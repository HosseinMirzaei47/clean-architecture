package com.speaking.partner.domain.usecases.task

import com.speaking.partner.domain.baseusecases.UseCase
import com.speaking.partner.domain.coroutineUtils.IoDispatcher
import com.speaking.partner.domain.notification.TaskAlarmManager
import com.speaking.partner.domain.repositories.reminder.ReminderRepository
import com.speaking.partner.domain.repositories.task.TaskRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskAlarmManager: TaskAlarmManager,
    private val reminderRepository: ReminderRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Long, Unit>(dispatcher) {
    override suspend fun execute(parameters: Long) {
        parameters.let { task ->
            taskRepository.deleteTask(task)
            taskRepository.deleteTaskCategoryRelations(task)
            reminderRepository.deleteAllRemindersOfTask(task)
            taskAlarmManager.cancelAlarmForTask(task)
        }
    }
}