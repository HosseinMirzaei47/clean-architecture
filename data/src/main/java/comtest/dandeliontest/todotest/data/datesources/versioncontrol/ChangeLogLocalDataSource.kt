package comtest.dandeliontest.todotest.data.datesources.versioncontrol

import comtest.dandeliontest.todotest.model_android.datastore.ChangeLogInformationModel
import kotlinx.coroutines.flow.Flow

interface ChangeLogLocalDataSource {

    suspend fun saveChangeLog(updateVersion: ChangeLogInformationModel)

    fun getChangeLog(): Flow<ChangeLogInformationModel>

    suspend fun clearChangeLog()
}