package com.speaking.partner.domain.usecases.task

import androidx.sqlite.db.SupportSQLiteQuery
import com.speaking.partner.domain.baseusecases.FlowUseCase
import com.speaking.partner.domain.coroutineUtils.IoDispatcher
import com.speaking.partner.domain.repositories.task.TaskRepository
import com.speaking.partner.model.models.task.Task
import com.speaking.partner.shared.resource.Resource
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
