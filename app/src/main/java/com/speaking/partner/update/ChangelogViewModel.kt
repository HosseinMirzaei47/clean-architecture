package com.speaking.partner.update

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.part.livetaskcore.usecases.asLiveTask
import com.speaking.partner.domain.usecases.versioncontrol.ClearChangeLogUseCase
import com.speaking.partner.domain.usecases.versioncontrol.GetChangeLogUseCase
import com.speaking.partner.model.models.update.UpdateInformation
import com.speaking.partner.ui.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangelogViewModel @Inject constructor(
    getChangeLogUseCase: GetChangeLogUseCase,
    clearChangeLogUseCase: ClearChangeLogUseCase,
) : ViewModel() {

    private var _changeLog = MutableLiveData<List<String>>()
    val changeLog: LiveData<List<String>> get() = _changeLog

    private var _onConfirmedClick = MutableLiveData<Event<Unit>>()
    val onConfirmedClick: LiveData<Event<Unit>> get() = _onConfirmedClick

    private val changeLogLiveTask = getChangeLogUseCase.asLiveTask {
        onSuccess<UpdateInformation> {
            if (!it.changeList.isNullOrEmpty()) {
                _changeLog.postValue(it.changeList)
                viewModelScope.launch {
                    clearChangeLogLiveTask(Unit)
                }
            }
        }
    }

    private val clearChangeLogLiveTask = clearChangeLogUseCase.asLiveTask()

    init {
        viewModelScope.launch {
            changeLogLiveTask(Unit)
        }
    }

    fun onConfirmedClicked() {
        _onConfirmedClick.value = Event(Unit)
    }
}