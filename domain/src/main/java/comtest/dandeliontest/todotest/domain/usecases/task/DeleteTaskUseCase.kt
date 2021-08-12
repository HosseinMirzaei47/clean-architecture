package comtest.dandeliontest.todotest.domain.usecases.task

import comtest.dandeliontest.todotest.domain.baseusecases.UseCase
import comtest.dandeliontest.todotest.domain.coroutineUtils.IoDispatcher
import comtest.dandeliontest.todotest.domain.notification.TaskAlarmManager
import comtest.dandeliontest.todotest.domain.repositories.reminder.ReminderRepository
import comtest.dandeliontest.todotest.domain.repositories.task.TaskRepository
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