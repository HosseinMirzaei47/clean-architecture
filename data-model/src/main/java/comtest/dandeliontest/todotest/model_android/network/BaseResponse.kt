package comtest.dandeliontest.todotest.model_android.network

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T, R>(
    val data: T,
    val meta: R,
)