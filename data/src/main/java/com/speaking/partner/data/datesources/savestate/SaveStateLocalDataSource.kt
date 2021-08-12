package com.speaking.partner.data.datesources.savestate

import com.speaking.partner.model_android.datastore.SavedStateInformationModel
import kotlinx.coroutines.flow.Flow

interface SaveStateLocalDataSource {
    suspend fun saveDestinationInfo(destinationInfo: SavedStateInformationModel)

    fun getCurrentDestinationInfo(): Flow<SavedStateInformationModel>

}