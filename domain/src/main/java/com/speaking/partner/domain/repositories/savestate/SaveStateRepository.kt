package com.speaking.partner.domain.repositories.savestate

import com.speaking.partner.model.models.SavedStateInformation
import kotlinx.coroutines.flow.Flow

interface SaveStateRepository {

    suspend fun saveDestinationInfo(destinationInfo: SavedStateInformation)

    fun getCurrentDestinationInfo(): Flow<SavedStateInformation>
}