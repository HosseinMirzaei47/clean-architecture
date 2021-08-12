package comtest.dandeliontest.todotest.domain.usecases.versioncontrol

import comtest.dandeliontest.todotest.domain.baseusecases.UseCase
import comtest.dandeliontest.todotest.domain.coroutineUtils.IoDispatcher
import comtest.dandeliontest.todotest.domain.repositories.versioncontrol.VersionControlRepository
import comtest.dandeliontest.todotest.model.models.update.UpdateInformation
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class CheckUpdateUseCase @Inject constructor(
    private val versionControlRepository: VersionControlRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher,
) : UseCase<String, UpdateInformation>(dispatcher) {
    override suspend fun execute(parameters: String): UpdateInformation =
        versionControlRepository.checkForNewUpdates(parameters)
}
