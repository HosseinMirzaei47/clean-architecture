package com.speaking.partner.domain.repositories.versioncontrol

import com.speaking.partner.model.models.update.UpdateInformation
import kotlinx.coroutines.flow.Flow

interface ChangeLogRepository {
    suspend fun saveChangeLog(updateVersion: UpdateInformation)

    fun getChangeLog(): Flow<UpdateInformation>

    suspend fun clearChangeLog()
}