package comtest.dandeliontest.todotest.data.mappers

import comtest.dandeliontest.todotest.model.models.update.UpdateInformation
import comtest.dandeliontest.todotest.model_android.datastore.ChangeLogInformationModel
import comtest.dandeliontest.todotest.model_android.network.UpdateVersion

fun UpdateVersion.toUpdateInformation(): UpdateInformation =
    UpdateInformation(
        isUpdateAvailable = true,
        isForceUpdate = isForceUpdate,
        version = version,
        changeList = changes,
    )

fun ChangeLogInformationModel.toUpdateInformation(): UpdateInformation =
    UpdateInformation(
        isUpdateAvailable = isUpdateAvailable,
        isForceUpdate = isForceUpdate,
        version = version,
        isShowed = isShowed,
        changeList = changeList,
    )

fun UpdateInformation.toChangeLogInformationModel() =
    ChangeLogInformationModel(
        isUpdateAvailable = isUpdateAvailable,
        isForceUpdate = isForceUpdate,
        version = version,
        isShowed = isShowed,
        changeList = changeList,
    )