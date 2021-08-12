package comtest.dandeliontest.todotest.data.datesources.savestate

import comtest.dandeliontest.todotest.model_android.datastore.SavedStateInformationModel
import kotlinx.coroutines.flow.Flow

interface SaveStateLocalDataSource {
    suspend fun saveDestinationInfo(destinationInfo: SavedStateInformationModel)

    fun getCurrentDestinationInfo(): Flow<SavedStateInformationModel>

}