package comtest.dandeliontest.todotest.domain.usecases.task

import comtest.dandeliontest.todotest.domain.baseusecases.FlowUseCase
import comtest.dandeliontest.todotest.domain.coroutineUtils.IoDispatcher
import comtest.dandeliontest.todotest.domain.repositories.task.TaskRepository
import comtest.dandeliontest.todotest.model.models.task.TodayTasksFilterModel
import comtest.dandeliontest.todotest.shared.resource.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTodayTasksCountUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<TodayTasksFilterModel, Int>(dispatcher) {
    override fun execute(parameter: TodayTasksFilterModel): Flow<Resource<Int>> =
        taskRepository.getTodayTasksCount(
            startMillis = parameter.startMillis,
            endMillis = parameter.endMillis
        ).map {
            Resource.Success(it)
        }
}