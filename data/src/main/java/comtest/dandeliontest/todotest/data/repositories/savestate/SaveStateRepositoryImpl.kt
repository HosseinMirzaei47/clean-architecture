package comtest.dandeliontest.todotest.data.repositories.savestate

import comtest.dandeliontest.todotest.data.datesources.savestate.SaveStateLocalDataSource
import comtest.dandeliontest.todotest.data.mappers.toSavedStateInformation
import comtest.dandeliontest.todotest.data.mappers.toSavedStateInformationModel
import comtest.dandeliontest.todotest.domain.repositories.savestate.SaveStateRepository
import comtest.dandeliontest.todotest.model.models.SavedStateInformation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SaveStateRepositoryImpl @Inject constructor(
    private val dataSource: SaveStateLocalDataSource
) : SaveStateRepository {

    override suspend fun saveDestinationInfo(destinationInfo: SavedStateInformation) =
        dataSource.saveDestinationInfo(destinationInfo.toSavedStateInformationModel())

    override fun getCurrentDestinationInfo(): Flow<SavedStateInformation> =
        dataSource.getCurrentDestinationInfo().map { it.toSavedStateInformation() }
}