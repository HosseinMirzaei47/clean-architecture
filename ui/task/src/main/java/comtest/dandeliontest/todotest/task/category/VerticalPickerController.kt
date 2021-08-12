package comtest.dandeliontest.todotest.task.category

import com.airbnb.epoxy.TypedEpoxyController
import comtest.dandeliontest.todotest.model.models.task.VerticalPickerModel
import comtest.dandeliontest.todotest.task.verticalPicker
import comtest.dandeliontest.todotest.ui.utils.EpoxyEventListener

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