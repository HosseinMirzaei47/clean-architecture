package com.speaking.partner.update

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.speaking.partner.BuildConfig
import com.speaking.partner.domain.usecases.versioncontrol.CheckUpdateUseCase
import com.speaking.partner.domain.usecases.versioncontrol.GetChangeLogUseCase
import com.speaking.partner.domain.usecases.versioncontrol.SaveChangeLogUseCase
import com.speaking.partner.shared.resource.Resource
import com.speaking.partner.shared.resource.requireData
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class VersionControlWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val getChangeLogUseCase: GetChangeLogUseCase,
    private val updateUseCase: CheckUpdateUseCase,
    private val saveChangeLogUseCase: SaveChangeLogUseCase,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return when (val result = updateUseCase(BuildConfig.VERSION_NAME)) {
            is Resource.Success -> {
                val lastUpdateInformation = getChangeLogUseCase(Unit).first()
                if (lastUpdateInformation.requireData().version != result.data.version) {
                    saveChangeLogUseCase.invoke(result.data)
                }

                Result.success()
            }
            else -> {
                Result.failure()
            }
        }
    }

    companion object {
        const val WORKER_NAME = "update-app-worker"
    }
}