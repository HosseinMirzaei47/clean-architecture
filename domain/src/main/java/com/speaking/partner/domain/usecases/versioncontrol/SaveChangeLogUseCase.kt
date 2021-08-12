package com.speaking.partner.domain.usecases.versioncontrol

import com.speaking.partner.domain.baseusecases.UseCase
import com.speaking.partner.domain.coroutineUtils.IoDispatcher
import com.speaking.partner.domain.repositories.versioncontrol.ChangeLogRepository
import com.speaking.partner.model.models.update.UpdateInformation
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SaveChangeLogUseCase @Inject constructor(
    private val changeLogRepository: ChangeLogRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<UpdateInformation, Unit>(dispatcher) {
    override suspend fun execute(parameters: UpdateInformation): Unit =
        changeLogRepository.saveChangeLog(parameters)
}