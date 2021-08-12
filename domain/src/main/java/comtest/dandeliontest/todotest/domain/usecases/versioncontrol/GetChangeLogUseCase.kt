package comtest.dandeliontest.todotest.domain.usecases.versioncontrol

import comtest.dandeliontest.todotest.domain.baseusecases.FlowUseCase
import comtest.dandeliontest.todotest.domain.coroutineUtils.IoDispatcher
import comtest.dandeliontest.todotest.domain.repositories.versioncontrol.ChangeLogRepository
import comtest.dandeliontest.todotest.model.models.update.UpdateInformation
import comtest.dandeliontest.todotest.shared.resource.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetChangeLogUseCase @Inject constructor(
    private val changeLogRepository: ChangeLogRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, UpdateInformation>(dispatcher) {
    override fun execute(parameter: Unit): Flow<Resource<UpdateInformation>> =
        changeLogRepository.getChangeLog().map {
            Resource.Success(it)
        }
}
