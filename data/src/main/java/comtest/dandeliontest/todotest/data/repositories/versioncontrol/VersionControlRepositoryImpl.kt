package comtest.dandeliontest.todotest.data.repositories.versioncontrol

import comtest.dandeliontest.todotest.data.datesources.versioncontrol.VersionControlRemoteDataSource
import comtest.dandeliontest.todotest.domain.repositories.versioncontrol.VersionControlRepository
import comtest.dandeliontest.todotest.model.models.update.UpdateInformation
import comtest.dandeliontest.todotest.shared.AppConstants
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