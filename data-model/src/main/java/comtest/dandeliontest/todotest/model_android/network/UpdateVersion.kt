package comtest.dandeliontest.todotest.model_android.network

import kotlinx.serialization.Serializable

@Serializable
data class UpdateVersion(
    val changes: List<String> = listOf(),
    val isForceUpdate: Boolean = false,
    val version: String = "",
)