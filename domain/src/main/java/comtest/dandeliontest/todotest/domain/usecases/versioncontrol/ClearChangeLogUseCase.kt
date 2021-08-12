package comtest.dandeliontest.todotest.domain.usecases.versioncontrol

import comtest.dandeliontest.todotest.domain.baseusecases.UseCase
import comtest.dandeliontest.todotest.domain.coroutineUtils.IoDispatcher
import comtest.dandeliontest.todotest.domain.repositories.versioncontrol.ChangeLogRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class ClearChangeLogUseCase @Inject constructor(
    private val changeLogRepository: ChangeLogRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Unit, Unit>(dispatcher) {
    override suspend fun execute(parameters: Unit): Unit =
        changeLogRepository.clearChangeLog()
}