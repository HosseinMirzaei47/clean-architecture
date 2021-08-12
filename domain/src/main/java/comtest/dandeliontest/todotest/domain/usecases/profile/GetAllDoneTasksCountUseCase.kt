package comtest.dandeliontest.todotest.domain.usecases.profile

import comtest.dandeliontest.todotest.domain.baseusecases.FlowUseCase
import comtest.dandeliontest.todotest.domain.coroutineUtils.IoDispatcher
import comtest.dandeliontest.todotest.domain.repositories.task.TaskRepository
import comtest.dandeliontest.todotest.shared.resource.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllDoneTasksCountUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, Int>(dispatcher) {
    override fun execute(parameter: Unit): Flow<Resource<Int>> =
        taskRepository.getAllDoneTasksCount().map {
            Resource.Success(it)
        }
}