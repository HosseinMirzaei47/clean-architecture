package comtest.dandeliontest.todotest.task.task

import com.airbnb.epoxy.TypedEpoxyController
import comtest.dandeliontest.todotest.model.models.task.PickerItem
import comtest.dandeliontest.todotest.model.models.task.PickerModel
import comtest.dandeliontest.todotest.task.horizontalPicker
import comtest.dandeliontest.todotest.ui.utils.EpoxyEventListener

class HorizontalPickerController(
    var isSelected: ((pickerItem: PickerItem) -> Boolean)? = null,
    private val onItemClicked: EpoxyEventListener,
) : TypedEpoxyController<PickerModel>() {
    override fun buildModels(data: PickerModel) {
        data.items.forEach { item ->
            val isSelected = isSelected?.let { it(item) } ?: false
            horizontalPicker {
                id(item.id)
                label(item.label)
                info(item.info)
                icon(item.icon)
                isSelected(isSelected)
                onClick { this@HorizontalPickerController.onItemClicked.onEvent(item.id to data.itemType) }
                background(if (isSelected) item.activatedBackground else item.inactiveBackground)
            }
        }
    }
}