package com.speaking.partner.data.datesources.versioncontrol

import com.speaking.partner.model_android.network.BaseResponse
import com.speaking.partner.model_android.network.UpdateAvailable
import com.speaking.partner.model_android.network.UpdateVersion

interface VersionControlRemoteDataSource {
    suspend fun checkForUpdates(
        app: String,
        platform: String,
        version: String
    ): BaseResponse<UpdateVersion, UpdateAvailable>
}