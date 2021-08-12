package comtest.dandeliontest.todotest.domain.usecases.savestate

import comtest.dandeliontest.todotest.domain.baseusecases.FlowUseCase
import comtest.dandeliontest.todotest.domain.coroutineUtils.IoDispatcher
import comtest.dandeliontest.todotest.domain.repositories.savestate.SaveStateRepository
import comtest.dandeliontest.todotest.model.models.SavedStateInformation
import comtest.dandeliontest.todotest.shared.resource.Resource
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