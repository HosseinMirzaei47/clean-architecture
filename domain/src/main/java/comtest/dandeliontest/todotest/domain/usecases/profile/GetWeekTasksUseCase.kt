package comtest.dandeliontest.todotest.domain.usecases.profile

import comtest.dandeliontest.todotest.domain.baseusecases.FlowUseCase
import comtest.dandeliontest.todotest.domain.coroutineUtils.IoDispatcher
import comtest.dandeliontest.todotest.domain.repositories.task.TaskRepository
import comtest.dandeliontest.todotest.model.models.task.Task
import comtest.dandeliontest.todotest.shared.resource.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetWeekTasksUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher,
) : FlowUseCase<Pair<Long, Long>, List<Task>>(dispatcher) {
    override fun execute(parameter: Pair<Long, Long>): Flow<Resource<List<Task>>> =
        taskRepository.getWeekTasks(parameter.first, parameter.second).map { taskEntityList ->
            Resource.Success(taskEntityList)
        }
}