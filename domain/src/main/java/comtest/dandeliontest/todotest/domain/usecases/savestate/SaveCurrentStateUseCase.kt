package comtest.dandeliontest.todotest.domain.usecases.savestate

import comtest.dandeliontest.todotest.domain.baseusecases.SuspendUseCase
import comtest.dandeliontest.todotest.domain.coroutineUtils.IoDispatcher
import comtest.dandeliontest.todotest.domain.repositories.savestate.SaveStateRepository
import comtest.dandeliontest.todotest.model.models.SavedStateInformation
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