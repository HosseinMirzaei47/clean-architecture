package comtest.dandeliontest.todotest.profile.status

import com.airbnb.epoxy.TypedEpoxyController
import comtest.dandeliontest.todotest.model.models.task.Status
import comtest.dandeliontest.todotest.ui.utils.EpoxyEventListener

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