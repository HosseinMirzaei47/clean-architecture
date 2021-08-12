package comtest.dandeliontest.todotest.data.datesources.versioncontrol

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import comtest.dandeliontest.todotest.data.datastore.proto.ChangeLogInformation
import comtest.dandeliontest.todotest.data.datastore.proto.ChangeLogInformationSerializable
import comtest.dandeliontest.todotest.data.utils.DataStoreConstants
import comtest.dandeliontest.todotest.model_android.datastore.ChangeLogInformationModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChangeLogLocalDataSourceImpl @Inject constructor(
    @ApplicationContext
    private val context: Context,
) : ChangeLogLocalDataSource {

    private val Context.userPreferencesStore: DataStore<ChangeLogInformation> by dataStore(
        fileName = DataStoreConstants.CHANGELOG_DATA_STORE_NAME,
        serializer = ChangeLogInformationSerializable
    )

    override suspend fun saveChangeLog(updateVersion: ChangeLogInformationModel) {
        context.userPreferencesStore.updateData {
            it.toBuilder().apply {
                isUpdateAvailable = updateVersion.isUpdateAvailable
                isForceUpdate = updateVersion.isForceUpdate
                version = updateVersion.version
                isShowed = updateVersion.isShowed
                addAllChanges(updateVersion.changeList)
            }.build()
        }
    }

    override fun getChangeLog(): Flow<ChangeLogInformationModel> =
        context.userPreferencesStore.data.map {
            ChangeLogInformationModel(
                it.isUpdateAvailable,
                it.isForceUpdate,
                it.version,
                it.isShowed,
                it.changesList
            )
        }

    override suspend fun clearChangeLog() {
        context.userPreferencesStore.updateData {
            it.toBuilder().clear().build()
        }
    }
}
