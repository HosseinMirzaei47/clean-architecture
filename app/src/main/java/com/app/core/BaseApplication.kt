package com.app.core

import android.app.Application
import com.airbnb.epoxy.EpoxyAsyncUtil
import com.airbnb.epoxy.EpoxyController
import com.app.core.shared.resource.Resource
import com.app.core.ui.utils.getMessage
import com.app.core.ui.utils.toLiveTaskResource
import com.part.binidng.views.addDefaultClassicViewTypes
import com.part.livetaskcore.LiveTaskManager
import com.part.livetaskcore.livatask.ViewException
import com.part.livetaskcore.Resource as LiveTaskResult

open class BaseApplication : Application() {

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

}