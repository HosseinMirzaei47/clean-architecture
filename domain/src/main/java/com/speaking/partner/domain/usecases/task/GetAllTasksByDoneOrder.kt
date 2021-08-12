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

class GetAllTasksByDoneOrder @Inject constructor(
    private val taskRepository: TaskRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher,
) : FlowUseCase<Unit, List<Task>>(dispatcher) {
    override fun execute(parameter: Unit): Flow<Resource<List<Task>>> =
        taskRepository.getAllTaskWithRemindersOrderByDone().map { Resource.Success(it) }
}