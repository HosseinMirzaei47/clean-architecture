package comtest.dandeliontest.todotest.domain.usecases.settings;

import comtest.dandeliontest.todotest.domain.baseusecases.SuspendUseCase
import comtest.dandeliontest.todotest.domain.coroutineUtils.IoDispatcher
import comtest.dandeliontest.todotest.domain.repositories.settings.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SetChosenThemeUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : SuspendUseCase<Int, Unit>(dispatcher) {
    override suspend fun execute(parameters: Int) =
        settingsRepository.setCurrentTheme(parameters)
}
