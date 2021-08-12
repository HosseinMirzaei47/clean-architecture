package com.speaking.partner.domain.usecases.profile

import com.speaking.partner.domain.baseusecases.FlowUseCase
import com.speaking.partner.domain.coroutineUtils.IoDispatcher
import com.speaking.partner.domain.repositories.task.TaskRepository
import com.speaking.partner.model.models.task.Task
import com.speaking.partner.shared.resource.Resource
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