package comtest.dandeliontest.todotest.domain.usecases.task

import comtest.dandeliontest.todotest.domain.baseusecases.UseCase
import comtest.dandeliontest.todotest.domain.coroutineUtils.IoDispatcher
import comtest.dandeliontest.todotest.domain.notification.TaskAlarmManager
import comtest.dandeliontest.todotest.domain.repositories.task.TaskRepository
import comtest.dandeliontest.todotest.model.models.task.IdWithPendingIntent
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