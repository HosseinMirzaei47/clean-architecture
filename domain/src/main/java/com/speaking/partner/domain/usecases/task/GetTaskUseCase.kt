package com.speaking.partner.domain.usecases.task

import com.speaking.partner.domain.baseusecases.FlowUseCase
import com.speaking.partner.domain.coroutineUtils.IoDispatcher
import com.speaking.partner.domain.repositories.task.TaskRepository
import com.speaking.partner.model.models.task.Task
import com.speaking.partner.shared.resource.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher,
) : FlowUseCase<Long, Task>(dispatcher) {
    override fun execute(parameter: Long): Flow<Resource<Task>> =
        taskRepository.getTaskWithRemindersAndTags(parameter).map { Resource.Success(it) }
}