package com.app.core.model.models.task

data class PickerItem(
    val id: Int,
    val label: String = "", // Main Title
    var info: String = "", // Description
    val icon: Int? = null,
    val activatedBackground: Int? = null,
    val inactiveBackground: Int? = null,
)