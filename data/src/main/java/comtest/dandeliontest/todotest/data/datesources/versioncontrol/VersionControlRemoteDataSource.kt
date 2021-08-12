package comtest.dandeliontest.todotest.data.datesources.versioncontrol

import comtest.dandeliontest.todotest.model_android.network.BaseResponse
import comtest.dandeliontest.todotest.model_android.network.UpdateAvailable
import comtest.dandeliontest.todotest.model_android.network.UpdateVersion

interface VersionControlRemoteDataSource {
    suspend fun checkForUpdates(
        app: String,
        platform: String,
        version: String
    ): BaseResponse<UpdateVersion, UpdateAvailable>
}