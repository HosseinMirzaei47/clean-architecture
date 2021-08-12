package com.speaking.partner.model_android.network

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T, R>(
    val data: T,
    val meta: R,
)