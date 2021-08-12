package comtest.dandeliontest.todotest.domain.usecases.task

import comtest.dandeliontest.todotest.domain.baseusecases.UseCase
import comtest.dandeliontest.todotest.domain.coroutineUtils.IoDispatcher
import comtest.dandeliontest.todotest.domain.repositories.task.TaskRepository
import comtest.dandeliontest.todotest.model.models.task.Task
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetRawTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher,
) : UseCase<Long, Task>(dispatcher) {
    override suspend fun execute(parameters: Long): Task {
        return taskRepository.getTask(parameters)
    }
}
