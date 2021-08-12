package com.speaking.partner.domain.usecases.versioncontrol

import com.speaking.partner.domain.baseusecases.FlowUseCase
import com.speaking.partner.domain.coroutineUtils.IoDispatcher
import com.speaking.partner.domain.repositories.versioncontrol.ChangeLogRepository
import com.speaking.partner.model.models.update.UpdateInformation
import com.speaking.partner.shared.resource.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetChangeLogUseCase @Inject constructor(
    private val changeLogRepository: ChangeLogRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, UpdateInformation>(dispatcher) {
    override fun execute(parameter: Unit): Flow<Resource<UpdateInformation>> =
        changeLogRepository.getChangeLog().map {
            Resource.Success(it)
        }
}
