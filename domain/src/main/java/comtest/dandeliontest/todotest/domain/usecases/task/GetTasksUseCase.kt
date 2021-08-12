package comtest.dandeliontest.todotest.domain.usecases.task

import androidx.sqlite.db.SupportSQLiteQuery
import comtest.dandeliontest.todotest.domain.baseusecases.FlowUseCase
import comtest.dandeliontest.todotest.domain.coroutineUtils.IoDispatcher
import comtest.dandeliontest.todotest.domain.repositories.task.TaskRepository
import comtest.dandeliontest.todotest.model.models.task.Task
import comtest.dandeliontest.todotest.shared.resource.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher,
) : FlowUseCase<SupportSQLiteQuery, List<Task>>(dispatcher) {
    override fun execute(parameter: SupportSQLiteQuery): Flow<Resource<List<Task>>> =
        taskRepository.filterTasks(parameter).map {
            Resource.Success(it.map { taskWithReminders -> taskWithReminders })
        }
}
