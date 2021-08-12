package com.speaking.partner.domain.usecases.savestate

import com.speaking.partner.domain.baseusecases.FlowUseCase
import com.speaking.partner.domain.coroutineUtils.IoDispatcher
import com.speaking.partner.domain.repositories.savestate.SaveStateRepository
import com.speaking.partner.model.models.SavedStateInformation
import com.speaking.partner.shared.resource.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCurrentStateUseCase @Inject constructor(
    private val saveStateRepository: SaveStateRepository,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, SavedStateInformation>(ioDispatcher) {
    override fun execute(parameter: Unit): Flow<Resource<SavedStateInformation>> =
        saveStateRepository.getCurrentDestinationInfo().map {
            Resource.Success(it)
        }
}