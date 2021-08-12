package com.speaking.partner.domain.repositories.versioncontrol

import com.speaking.partner.model.models.update.UpdateInformation

interface VersionControlRepository {
    suspend fun checkForNewUpdates(version: String): UpdateInformation
}