package com.speaking.partner.data.datesources.versioncontrol

import com.speaking.partner.data.api.VersionApiService
import com.speaking.partner.data.utils.bodyOrThrow
import com.speaking.partner.model_android.network.BaseResponse
import com.speaking.partner.model_android.network.UpdateAvailable
import com.speaking.partner.model_android.network.UpdateVersion
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