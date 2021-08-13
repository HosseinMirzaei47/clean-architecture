package com.app.core.model.models.task

data class PickerModel(
    var itemType: String,
    val items: List<PickerItem>,
    var selectedItems: MutableList<Int> = mutableListOf(),
    var selectedItem: Int,
)