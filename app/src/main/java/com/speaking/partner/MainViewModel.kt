package com.speaking.partner

import androidx.lifecycle.*
import com.part.livetaskcore.usecases.asLiveTask
import com.speaking.partner.domain.usecases.savestate.GetCurrentStateUseCase
import com.speaking.partner.domain.usecases.settings.GetChosenThemeUseCase
import com.speaking.partner.domain.usecases.settings.GetLanguageUseCase
import com.speaking.partner.domain.usecases.versioncontrol.GetChangeLogUseCase
import com.speaking.partner.model.models.SavedStateInformation
import com.speaking.partner.model.models.update.UpdateInformation
import com.speaking.partner.notification.TaskManager
import com.speaking.partner.ui.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    savedState: SavedStateHandle,
    getChosenThemeUseCase: GetChosenThemeUseCase,
    getLanguageUseCase: GetLanguageUseCase,
    private val taskManager: TaskManager,
    getChangeLogUseCase: GetChangeLogUseCase,
    getCurrentStateUseCase: GetCurrentStateUseCase,
) : ViewModel() {

    private var firstLaunch = false

    private var isHandled = false

    private var _languageChanged = MutableLiveData<Event<Unit>>()
    val languageChanged: LiveData<Event<Unit>> get() = _languageChanged

    private var _chosenTheme = MutableLiveData<Event<Int>>()
    val chosenTheme: LiveData<Event<Int>> get() = _chosenTheme

    private var _bottomNavIsGone = MutableLiveData<Boolean>()
    val bottomNavIsGone: LiveData<Boolean> get() = _bottomNavIsGone

    private val _updateStatus = MutableLiveData<Int>(VERSION_CONTROL_TYPE_NONE)
    val updateStatus: LiveData<Int> = _updateStatus

    private var _navigateToSetting = MutableLiveData<Event<Unit>>()
    val navigateToSetting: LiveData<Event<Unit>> get() = _navigateToSetting

    private var _currentDestination = MutableLiveData<Event<SavedStateInformation>>()
    val currentDestination: LiveData<Event<SavedStateInformation>> get() = _currentDestination

    private val languageLiveTask = getLanguageUseCase.asLiveTask {
        onSuccess<Int> {
            if (!firstLaunch) {
                firstLaunch = true
            } else {
                _languageChanged.postValue(Event(Unit))
            }
        }
    }

    private val changeLogLiveTask = getChangeLogUseCase.asLiveTask {
        onSuccess<UpdateInformation> {
            _updateStatus.value = when {
                it.version == BuildConfig.VERSION_NAME -> VERSION_CONTROL_TYPE_CHANGELOG
                it.isUpdateAvailable && it.isForceUpdate ->
                    VERSION_CONTROL_TYPE_FORCE
                it.isUpdateAvailable && !it.isShowed -> VERSION_CONTROL_TYPE_DIALOG
                else -> {
                    VERSION_CONTROL_TYPE_NONE
                }
            }
        }
    }

    private val stateLiveTask = getCurrentStateUseCase.asLiveTask {
        onSuccess<SavedStateInformation> { stateInfo ->
            if (!isHandled) {
                _currentDestination.postValue(Event(stateInfo))
                isHandled = true
            }
        }
    }

    init {
        viewModelScope.launch {
            languageLiveTask(Unit)
            changeLogLiveTask(Unit)
            stateLiveTask(Unit)
        }
        if (savedState.get<Boolean>("from_setting") == true) {
            _navigateToSetting.value = Event(Unit)
        } else {
            scheduleTasks()
        }
    }

    private fun scheduleTasks() {
        taskManager.firstRunNotificationWorker()
        taskManager.checkForUpdates()
    }

    companion object {
        const val VERSION_CONTROL_TYPE_NONE = 0
        const val VERSION_CONTROL_TYPE_FORCE = 1
        const val VERSION_CONTROL_TYPE_DIALOG = 2
        const val VERSION_CONTROL_TYPE_CHANGELOG = 3
    }
}