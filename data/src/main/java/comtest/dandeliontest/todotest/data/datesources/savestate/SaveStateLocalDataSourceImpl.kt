package comtest.dandeliontest.todotest.data.datesources.savestate

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import comtest.dandeliontest.todotest.data.datastore.proto.SavedStateInformation
import comtest.dandeliontest.todotest.data.datastore.proto.SavedStateInformationSerializable
import comtest.dandeliontest.todotest.data.utils.DataStoreConstants
import comtest.dandeliontest.todotest.model_android.datastore.SavedStateInformationModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SaveStateLocalDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SaveStateLocalDataSource {


    private val Context.stateDataStore: DataStore<SavedStateInformation> by dataStore(
        fileName = DataStoreConstants.STATE_DATA_STORE,
        serializer = SavedStateInformationSerializable
    )

    override suspend fun saveDestinationInfo(destinationInfo: SavedStateInformationModel) {
        context.stateDataStore.updateData {
            it.toBuilder().apply {
                latestDestinationId = destinationInfo.latestDestinationId
                categoryId = destinationInfo.categoryId
            }.build()
        }
    }

    override fun getCurrentDestinationInfo(): Flow<SavedStateInformationModel> {
        return context.stateDataStore.data.map {
            SavedStateInformationModel(
                latestDestinationId = it.latestDestinationId,
                categoryId = it.categoryId,
            )
        }
    }
}