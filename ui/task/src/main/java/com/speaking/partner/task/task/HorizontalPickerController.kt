package com.speaking.partner.task.task

import com.airbnb.epoxy.TypedEpoxyController
import com.speaking.partner.model.models.task.PickerItem
import com.speaking.partner.model.models.task.PickerModel
import com.speaking.partner.task.horizontalPicker
import com.speaking.partner.ui.utils.EpoxyEventListener

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