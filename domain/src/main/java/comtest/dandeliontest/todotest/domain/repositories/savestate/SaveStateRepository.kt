package comtest.dandeliontest.todotest.domain.repositories.savestate

import comtest.dandeliontest.todotest.model.models.SavedStateInformation
import kotlinx.coroutines.flow.Flow

interface SaveStateRepository {

    suspend fun saveDestinationInfo(destinationInfo: SavedStateInformation)

    fun getCurrentDestinationInfo(): Flow<SavedStateInformation>
}