package comtest.dandeliontest.todotest.data.datesources.versioncontrol

import comtest.dandeliontest.todotest.data.api.VersionApiService
import comtest.dandeliontest.todotest.data.utils.bodyOrThrow
import comtest.dandeliontest.todotest.model_android.network.BaseResponse
import comtest.dandeliontest.todotest.model_android.network.UpdateAvailable
import comtest.dandeliontest.todotest.model_android.network.UpdateVersion
import javax.inject.Inject

class VersionControlRemoteDataSourceImpl @Inject constructor(
    private val apiService: VersionApiService
) : VersionControlRemoteDataSource {
    override suspend fun checkForUpdates(
        app: String,
        platform: String,
        version: String
    ): BaseResponse<UpdateVersion, UpdateAvailable> {
        return apiService.checkForUpdates(app, platform, version).bodyOrThrow()
    }
}