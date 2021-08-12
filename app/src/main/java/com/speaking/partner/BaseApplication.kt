package com.speaking.partner

import android.app.Application
import com.airbnb.epoxy.EpoxyAsyncUtil
import com.airbnb.epoxy.EpoxyController
import com.part.binidng.views.addDefaultClassicViewTypes
import com.part.livetaskcore.LiveTaskManager
import com.part.livetaskcore.livatask.ViewException
import com.speaking.partner.shared.resource.Resource
import com.speaking.partner.ui.utils.getMessage
import com.speaking.partner.ui.utils.toLiveTaskResource
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