package comtest.dandeliontest.todotest.domain.repositories.versioncontrol

import comtest.dandeliontest.todotest.model.models.update.UpdateInformation
import kotlinx.coroutines.flow.Flow

interface ChangeLogRepository {
    suspend fun saveChangeLog(updateVersion: UpdateInformation)

    fun getChangeLog(): Flow<UpdateInformation>

    suspend fun clearChangeLog()
}