package com.speaking.partner.data.repositories.savestate

import com.speaking.partner.data.datesources.savestate.SaveStateLocalDataSource
import com.speaking.partner.data.mappers.toSavedStateInformation
import com.speaking.partner.data.mappers.toSavedStateInformationModel
import com.speaking.partner.domain.repositories.savestate.SaveStateRepository
import com.speaking.partner.model.models.SavedStateInformation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SaveStateRepositoryImpl @Inject constructor(
    private val dataSource: SaveStateLocalDataSource
) : SaveStateRepository {

    override suspend fun saveDestinationInfo(destinationInfo: SavedStateInformation) =
        dataSource.saveDestinationInfo(destinationInfo.toSavedStateInformationModel())

    override fun getCurrentDestinationInfo(): Flow<SavedStateInformation> =
        dataSource.getCurrentDestinationInfo().map { it.toSavedStateInformation() }
}