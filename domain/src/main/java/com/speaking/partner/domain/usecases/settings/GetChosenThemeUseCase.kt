package com.speaking.partner.domain.usecases.settings

import com.speaking.partner.domain.baseusecases.FlowUseCase
import com.speaking.partner.domain.coroutineUtils.IoDispatcher
import com.speaking.partner.domain.repositories.settings.SettingsRepository
import com.speaking.partner.shared.resource.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetChosenThemeUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, Int>(ioDispatcher) {
    override fun execute(parameter: Unit): Flow<Resource<Int>> =
        settingsRepository.getCurrentTheme().map {
            Resource.Success(it)
        }
}