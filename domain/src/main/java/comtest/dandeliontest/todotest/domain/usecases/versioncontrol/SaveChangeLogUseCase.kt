package comtest.dandeliontest.todotest.domain.usecases.versioncontrol

import comtest.dandeliontest.todotest.domain.baseusecases.UseCase
import comtest.dandeliontest.todotest.domain.coroutineUtils.IoDispatcher
import comtest.dandeliontest.todotest.domain.repositories.versioncontrol.ChangeLogRepository
import comtest.dandeliontest.todotest.model.models.update.UpdateInformation
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SaveChangeLogUseCase @Inject constructor(
    private val changeLogRepository: ChangeLogRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<UpdateInformation, Unit>(dispatcher) {
    override suspend fun execute(parameters: UpdateInformation): Unit =
        changeLogRepository.saveChangeLog(parameters)
}