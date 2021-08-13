package com.app.core.model.models.task

data class VerticalPickerModel(
    var itemType: String,
    var id: Long,
    var title: String,
    var isSelected: Boolean,
)