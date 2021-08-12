package comtest.dandeliontest.todotest.model_android.datastore

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ChangeLogInformationModel(
    val isUpdateAvailable: Boolean = false,
    val isForceUpdate: Boolean = false,
    val version: String = "",
    val isShowed: Boolean = false,
    val changeList: List<String> = listOf(),
) : Parcelable