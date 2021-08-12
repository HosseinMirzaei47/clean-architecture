package com.speaking.partner.domain.usecases.savestate

import com.speaking.partner.domain.baseusecases.SuspendUseCase
import com.speaking.partner.domain.coroutineUtils.IoDispatcher
import com.speaking.partner.domain.repositories.savestate.SaveStateRepository
import com.speaking.partner.model.models.SavedStateInformation
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SaveCurrentStateUseCase @Inject constructor(
    private val saveStateRepository: SaveStateRepository,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
) : SuspendUseCase<SavedStateInformation, Unit>(ioDispatcher) {

    override suspend fun execute(parameters: SavedStateInformation) {
        saveStateRepository.saveDestinationInfo(parameters)
    }
}