package comtest.dandeliontest.todotest

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.airbnb.epoxy.EpoxyAsyncUtil
import com.airbnb.epoxy.EpoxyController
import com.part.binidng.views.addDefaultClassicViewTypes
import com.part.livetaskcore.LiveTaskManager
import com.part.livetaskcore.livatask.ViewException
import comtest.dandeliontest.todotest.notification.HiltWorkerFactoryEntryPoint
import comtest.dandeliontest.todotest.shared.resource.Resource
import comtest.dandeliontest.todotest.ui.utils.getMessage
import comtest.dandeliontest.todotest.ui.utils.toLiveTaskResource
import dagger.hilt.android.EntryPointAccessors
import com.part.livetaskcore.Resource as LiveTaskResult

open class BaseApplication : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()
        setupEpoxyController()

        LiveTaskManager().Builder()
            .setErrorMapper { exception ->
                ViewException(exception.getMessage(this), exception)
            }
            .setResourceMapper {
                if (it is Resource<*>) it.toLiveTaskResource() else LiveTaskResult.Success(it)
            }
            .addDefaultClassicViewTypes()
            .apply()
    }

    private fun setupEpoxyController() {
        val handler = EpoxyAsyncUtil.getAsyncBackgroundHandler()
        EpoxyController.defaultDiffingHandler = handler
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(getHiltWorkerFactory())
            .build()
    }

    private fun getHiltWorkerFactory(): HiltWorkerFactory {
        return EntryPointAccessors.fromApplication(
            applicationContext,
            HiltWorkerFactoryEntryPoint::class.java
        ).getWorkerFactoryEntryPoint()
    }
}