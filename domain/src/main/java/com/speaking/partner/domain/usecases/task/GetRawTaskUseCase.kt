package com.speaking.partner.domain.usecases.task

import com.speaking.partner.domain.baseusecases.UseCase
import com.speaking.partner.domain.coroutineUtils.IoDispatcher
import com.speaking.partner.domain.repositories.task.TaskRepository
import com.speaking.partner.model.models.task.Task
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
