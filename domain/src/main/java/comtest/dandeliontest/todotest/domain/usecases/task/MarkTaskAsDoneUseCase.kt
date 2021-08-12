package comtest.dandeliontest.todotest.domain.usecases.task

import comtest.dandeliontest.todotest.domain.baseusecases.UseCase
import comtest.dandeliontest.todotest.domain.coroutineUtils.IoDispatcher
import comtest.dandeliontest.todotest.domain.notification.TaskAlarmManager
import comtest.dandeliontest.todotest.domain.repositories.task.TaskRepository
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