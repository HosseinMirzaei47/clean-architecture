package com.speaking.partner.profile.status

import com.airbnb.epoxy.TypedEpoxyController
import com.speaking.partner.model.models.task.Status
import com.speaking.partner.ui.utils.EpoxyEventListener

class StatusController(
    private val onClick: EpoxyEventListener? = null
) : TypedEpoxyController<List<Status>>() {
    override fun buildModels(data: List<Status>?) {
        data?.forEach { status ->
            status {
                id(status.id)
                title(status.title)
                color(status.color)
                icon(status.icon)
                count(status.count)
                clickListener {
                    this@StatusController.onClick?.onEvent(status.id)
                }
            }
        }

    }

}