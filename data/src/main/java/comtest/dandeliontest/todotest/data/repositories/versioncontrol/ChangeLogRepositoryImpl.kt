package comtest.dandeliontest.todotest.data.repositories.versioncontrol

import comtest.dandeliontest.todotest.data.datesources.versioncontrol.ChangeLogLocalDataSource
import comtest.dandeliontest.todotest.data.mappers.toChangeLogInformationModel
import comtest.dandeliontest.todotest.data.mappers.toUpdateInformation
import comtest.dandeliontest.todotest.domain.repositories.versioncontrol.ChangeLogRepository
import comtest.dandeliontest.todotest.model.models.update.UpdateInformation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChangeLogRepositoryImpl @Inject constructor(
    private val changeLogLocalDataSource: ChangeLogLocalDataSource
) : ChangeLogRepository {

    override suspend fun saveChangeLog(updateVersion: UpdateInformation) =
        changeLogLocalDataSource.saveChangeLog(updateVersion.toChangeLogInformationModel())

    override fun getChangeLog(): Flow<UpdateInformation> =
        changeLogLocalDataSource.getChangeLog().map { it.toUpdateInformation() }

    override suspend fun clearChangeLog() = changeLogLocalDataSource.clearChangeLog()
}