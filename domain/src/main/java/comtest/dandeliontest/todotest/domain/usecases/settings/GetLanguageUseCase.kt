package comtest.dandeliontest.todotest.domain.usecases.settings

import comtest.dandeliontest.todotest.domain.baseusecases.FlowUseCase
import comtest.dandeliontest.todotest.domain.coroutineUtils.IoDispatcher
import comtest.dandeliontest.todotest.domain.repositories.settings.SettingsRepository
import comtest.dandeliontest.todotest.shared.resource.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetLanguageUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, Int>(ioDispatcher) {
    override fun execute(parameter: Unit): Flow<Resource<Int>> =
        settingsRepository.getLanguage().map {
            Resource.Success(it)
        }
}