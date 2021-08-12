package com.speaking.partner.domain.usecases.settings;

import com.speaking.partner.domain.baseusecases.SuspendUseCase
import com.speaking.partner.domain.coroutineUtils.IoDispatcher
import com.speaking.partner.domain.repositories.settings.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SetChosenThemeUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : SuspendUseCase<Int, Unit>(dispatcher) {
    override suspend fun execute(parameters: Int) =
        settingsRepository.setCurrentTheme(parameters)
}
