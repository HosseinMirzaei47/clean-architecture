package comtest.dandeliontest.todotest.update

import androidx.lifecycle.*
import com.part.livetaskcore.usecases.asLiveTask
import comtest.dandeliontest.todotest.domain.usecases.versioncontrol.GetChangeLogUseCase
import comtest.dandeliontest.todotest.domain.usecases.versioncontrol.SaveChangeLogUseCase
import comtest.dandeliontest.todotest.shared.resource.requireData
import comtest.dandeliontest.todotest.ui.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateViewModel @Inject constructor(
    savedState: SavedStateHandle,
    getChangeLogUseCase: GetChangeLogUseCase,
    saveChangeLogUseCase: SaveChangeLogUseCase,
) : ViewModel() {

    private var _onIgnoreClicked = MutableLiveData<Event<Unit>>()
    val onIgnoreClicked: LiveData<Event<Unit>> get() = _onIgnoreClicked

    private var _onUpdateClicked = MutableLiveData<Event<Unit>>()
    val onUpdateClicked: LiveData<Event<Unit>> get() = _onUpdateClicked

    private var _isCancelable = MutableLiveData<Boolean>()
    val isCancelable: LiveData<Boolean> get() = _isCancelable

    private val saveChangeLogLiveTask = saveChangeLogUseCase.asLiveTask()

    init {
        savedState.get<Long>("isForce")?.let { isForce ->
            _isCancelable.value = isForce != 1L
        }
        viewModelScope.launch {
            val updateInformation =
                getChangeLogUseCase(Unit).first().requireData().copy(isShowed = true)
            saveChangeLogLiveTask(updateInformation)
        }
    }

    fun onIgnoreClicked() {
        _onIgnoreClicked.value = Event(Unit)
    }

    fun onUpdateClicked() {
        _onUpdateClicked.value = Event(Unit)
    }
}