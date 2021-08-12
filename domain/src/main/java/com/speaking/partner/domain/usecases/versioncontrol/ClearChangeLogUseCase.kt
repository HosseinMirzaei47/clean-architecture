package com.speaking.partner.domain.usecases.versioncontrol

import com.speaking.partner.domain.baseusecases.UseCase
import com.speaking.partner.domain.coroutineUtils.IoDispatcher
import com.speaking.partner.domain.repositories.versioncontrol.ChangeLogRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class ClearChangeLogUseCase @Inject constructor(
    private val changeLogRepository: ChangeLogRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Unit, Unit>(dispatcher) {
    override suspend fun execute(parameters: Unit): Unit =
        changeLogRepository.clearChangeLog()
}