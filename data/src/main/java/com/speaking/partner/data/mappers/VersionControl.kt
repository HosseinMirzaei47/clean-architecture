package com.speaking.partner.data.mappers

import com.speaking.partner.model.models.update.UpdateInformation
import com.speaking.partner.model_android.datastore.ChangeLogInformationModel
import com.speaking.partner.model_android.network.UpdateVersion

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