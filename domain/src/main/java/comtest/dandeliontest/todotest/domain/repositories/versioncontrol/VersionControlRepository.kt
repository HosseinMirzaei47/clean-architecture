package comtest.dandeliontest.todotest.domain.repositories.versioncontrol

import comtest.dandeliontest.todotest.model.models.update.UpdateInformation

interface VersionControlRepository {
    suspend fun checkForNewUpdates(version: String): UpdateInformation
}