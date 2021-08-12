package com.speaking.partner.domain.usecases.task

import com.speaking.partner.domain.baseusecases.FlowUseCase
import com.speaking.partner.domain.coroutineUtils.IoDispatcher
import com.speaking.partner.domain.repositories.task.TaskRepository
import com.speaking.partner.model.models.task.TodayTasksFilterModel
import com.speaking.partner.shared.resource.Resource
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