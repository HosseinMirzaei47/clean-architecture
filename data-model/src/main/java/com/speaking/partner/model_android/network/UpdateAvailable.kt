package com.speaking.partner.model_android.network

import kotlinx.serialization.Serializable

@Serializable
data class UpdateAvailable(
    val isUpdateAvailable: Boolean
)