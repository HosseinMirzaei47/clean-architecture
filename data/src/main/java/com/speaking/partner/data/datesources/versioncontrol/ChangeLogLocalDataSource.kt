package com.speaking.partner.data.datesources.versioncontrol

import com.speaking.partner.model_android.datastore.ChangeLogInformationModel
import kotlinx.coroutines.flow.Flow

interface ChangeLogLocalDataSource {

    suspend fun saveChangeLog(updateVersion: ChangeLogInformationModel)

    fun getChangeLog(): Flow<ChangeLogInformationModel>

    suspend fun clearChangeLog()
}