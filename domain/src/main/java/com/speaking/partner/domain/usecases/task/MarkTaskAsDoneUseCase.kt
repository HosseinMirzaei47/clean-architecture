package com.speaking.partner.domain.usecases.task

import com.speaking.partner.domain.baseusecases.UseCase
import com.speaking.partner.domain.coroutineUtils.IoDispatcher
import com.speaking.partner.domain.notification.TaskAlarmManager
import com.speaking.partner.domain.repositories.task.TaskRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class MarkTaskAsDoneUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskAlarmManager: TaskAlarmManager,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Long, Unit>(dispatcher) {
    override suspend fun execute(parameters: Long) = parameters.let { taskId ->
        taskRepository.markTaskAsDone(taskId)
        taskAlarmManager.cancelAlarmForTask(taskId)
    }
}