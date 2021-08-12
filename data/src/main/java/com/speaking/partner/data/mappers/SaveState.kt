package com.speaking.partner.data.mappers

import com.speaking.partner.model.models.SavedStateInformation
import com.speaking.partner.model_android.datastore.SavedStateInformationModel

fun SavedStateInformation.toSavedStateInformationModel() =
    SavedStateInformationModel(
        latestDestinationId = latestDestinationId,
        categoryId = categoryId
    )

fun SavedStateInformationModel.toSavedStateInformation() =
    SavedStateInformation(
        latestDestinationId = latestDestinationId,
        categoryId = categoryId
    )