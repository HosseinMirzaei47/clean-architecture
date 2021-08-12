package com.speaking.partner.data.api

import com.speaking.partner.model_android.network.BaseResponse
import com.speaking.partner.model_android.network.UpdateAvailable
import com.speaking.partner.model_android.network.UpdateVersion
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface VersionApiService {
    @GET("api/{app}/versions/{platform}")
    suspend fun checkForUpdates(
        @Path("app") app: String,
        @Path("platform") platform: String,
        @Query("current") currentVersion: String
    ): Response<BaseResponse<UpdateVersion, UpdateAvailable>>
}