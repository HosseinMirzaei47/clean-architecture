package comtest.dandeliontest.todotest.model_android.datastore

import androidx.annotation.Keep

@Keep
data class SavedStateInformationModel(
    val latestDestinationId: Long,
    val categoryId: Long
)