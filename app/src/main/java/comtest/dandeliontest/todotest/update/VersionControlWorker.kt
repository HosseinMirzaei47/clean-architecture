package comtest.dandeliontest.todotest.update

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import comtest.dandeliontest.todotest.BuildConfig
import comtest.dandeliontest.todotest.domain.usecases.versioncontrol.CheckUpdateUseCase
import comtest.dandeliontest.todotest.domain.usecases.versioncontrol.GetChangeLogUseCase
import comtest.dandeliontest.todotest.domain.usecases.versioncontrol.SaveChangeLogUseCase
import comtest.dandeliontest.todotest.shared.resource.Resource
import comtest.dandeliontest.todotest.shared.resource.requireData
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