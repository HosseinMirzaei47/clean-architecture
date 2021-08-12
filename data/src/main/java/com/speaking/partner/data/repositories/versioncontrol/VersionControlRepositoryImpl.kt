package com.speaking.partner.data.repositories.versioncontrol

import com.speaking.partner.data.datesources.versioncontrol.VersionControlRemoteDataSource
import com.speaking.partner.domain.repositories.versioncontrol.VersionControlRepository
import com.speaking.partner.model.models.update.UpdateInformation
import com.speaking.partner.shared.AppConstants
import javax.inject.Inject

class VersionControlRepositoryImpl @Inject constructor(
    private val remoteDataSource: VersionControlRemoteDataSource
) : VersionControlRepository {

    override suspend fun checkForNewUpdates(
        version: String
    ): UpdateInformation {
        val updateInformation =
            remoteDataSource.checkForUpdates(AppConstants.APP, AppConstants.PLATFORM, version)
        return if (updateInformation.meta.isUpdateAvailable) {
            UpdateInformation(
                isUpdateAvailable = true,
                isForceUpdate = updateInformation.data.isForceUpdate,
                version = updateInformation.data.version,
                changeList = updateInformation.data.changes,
            )
        } else {
            UpdateInformation()
        }
    }
}