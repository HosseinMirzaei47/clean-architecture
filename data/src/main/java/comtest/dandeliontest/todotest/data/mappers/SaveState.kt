package comtest.dandeliontest.todotest.data.mappers

import comtest.dandeliontest.todotest.model.models.SavedStateInformation
import comtest.dandeliontest.todotest.model_android.datastore.SavedStateInformationModel

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