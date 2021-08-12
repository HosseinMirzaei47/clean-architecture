package com.speaking.partner.model.models.update

data class UpdateInformation(
    val isUpdateAvailable: Boolean = false,
    val isForceUpdate: Boolean = false,
    val version: String = "",
    val isShowed: Boolean = false,
    val changeList: List<String> = listOf(),
)