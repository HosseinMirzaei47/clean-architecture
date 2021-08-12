package com.speaking.partner.data.repositories.versioncontrol

import com.speaking.partner.data.datesources.versioncontrol.ChangeLogLocalDataSource
import com.speaking.partner.data.mappers.toChangeLogInformationModel
import com.speaking.partner.data.mappers.toUpdateInformation
import com.speaking.partner.domain.repositories.versioncontrol.ChangeLogRepository
import com.speaking.partner.model.models.update.UpdateInformation
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