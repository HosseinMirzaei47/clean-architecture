package com.speaking.partner.domain.usecases.versioncontrol

import com.speaking.partner.domain.baseusecases.UseCase
import com.speaking.partner.domain.coroutineUtils.IoDispatcher
import com.speaking.partner.domain.repositories.versioncontrol.VersionControlRepository
import com.speaking.partner.model.models.update.UpdateInformation
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class CheckUpdateUseCase @Inject constructor(
    private val versionControlRepository: VersionControlRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher,
) : UseCase<String, UpdateInformation>(dispatcher) {
    override suspend fun execute(parameters: String): UpdateInformation =
        versionControlRepository.checkForNewUpdates(parameters)
}
