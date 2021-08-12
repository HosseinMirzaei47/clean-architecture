package com.speaking.partner.task.category

import com.airbnb.epoxy.TypedEpoxyController
import com.speaking.partner.model.models.task.VerticalPickerModel
import com.speaking.partner.task.verticalPicker
import com.speaking.partner.ui.utils.EpoxyEventListener

class VerticalPickerController(
    private val onItemClicked: EpoxyEventListener,
) : TypedEpoxyController<List<VerticalPickerModel>>() {
    override fun buildModels(data: List<VerticalPickerModel>?) {
        data?.forEach { pickerModel ->
            verticalPicker {
                id(pickerModel.id)
                title(pickerModel.title)
                isChecked(pickerModel.isSelected)
                checkedChangeListener {
                    pickerModel.isSelected = it as Boolean
                    this@VerticalPickerController.onItemClicked.onEvent(pickerModel.id to pickerModel.itemType)
                }
            }
        }
    }
}