package com.speaking.partner.task.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.part.livetaskcore.livatask.combine
import com.part.livetaskcore.usecases.asLiveTask
import com.speaking.partner.domain.usecases.settings.*
import com.speaking.partner.domain.utils.DataStoreConstants
import com.speaking.partner.task.R
import com.speaking.partner.ui.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val setChosenThemeUseCase: SetChosenThemeUseCase,
    private val setLanguageUseCase: SetLanguageUseCase,
    private val setTimeFormatUseCase: SetTimeFormatUseCase,
    getChosenThemeUseCase: GetChosenThemeUseCase,
    getLanguageUseCase: GetLanguageUseCase,
    getTimeFormatUseCase: GetTimeFormatUseCase,
) : ViewModel() {

    private var _onBackPressed = MutableLiveData<Event<Unit>>()
    val onBackPressed: LiveData<Event<Unit>> get() = _onBackPressed

    private var _onPrivacyClick = MutableLiveData<Event<Unit>>()
    val onPrivacyClick: LiveData<Event<Unit>> get() = _onPrivacyClick

    private var _onFeedBackClick = MutableLiveData<Event<Unit>>()
    val onFeedBackClick: LiveData<Event<Unit>> get() = _onFeedBackClick

    private var _onRateUsClick = MutableLiveData<Event<Unit>>()
    val onRateUsClick: LiveData<Event<Unit>> get() = _onRateUsClick

    private var _versionCode = MutableLiveData<String>()
    val versionCode: LiveData<String> get() = _versionCode

    var selectedThemeId = MutableLiveData<Int>()

    var selectedLanguageId = MutableLiveData<Int>()

    var selectedTimeFormatId = MutableLiveData<Int>()

    fun onThemeChanged(themeCode: Int) {
        viewModelScope.launch { setChosenThemeUseCase(themeCode) }
    }

    fun onAppLanguageChanged(languageCode: Int) {
        viewModelScope.launch { setLanguageUseCase(languageCode) }
    }

    fun onTimeFormatChanged(timeFormat: Int) {
        viewModelScope.launch { setTimeFormatUseCase(timeFormat) }
    }

    fun onBackPressed() {
        _onBackPressed.value = Event(Unit)
    }

    private val chosenLanguageLiveTask = getLanguageUseCase.asLiveTask {
        withParameter(Unit)
        onSuccess<Int> {
            when (it) {
                DataStoreConstants.Languages.ENGLISH -> {
                    selectedLanguageId.postValue(R.id.rb_english)
                }
                DataStoreConstants.Languages.PERSIAN -> {
                    selectedLanguageId.postValue(R.id.rb_persian)
                }
            }
        }
    }

    private val chosenThemeLiveTask = getChosenThemeUseCase.asLiveTask {
        withParameter(Unit)
        onSuccess<Int> {
            when (it) {
                DataStoreConstants.Theme.NIGHT -> {
                    selectedThemeId.postValue(R.id.rb_dark)
                }
                DataStoreConstants.Theme.LIGHT -> {
                    selectedThemeId.postValue(R.id.rb_light)
                }
                DataStoreConstants.Theme.SYS_DEF -> {
                    selectedThemeId.postValue(R.id.rb_theme_system_default)
                }
            }
        }
    }

    private val chosenTimeFormatLiveTask = getTimeFormatUseCase.asLiveTask {
        withParameter(Unit)
        onSuccess<Int> {
            when (it) {
                DataStoreConstants.TimeFormat.FORMAT_24 -> {
                    selectedTimeFormatId.postValue(R.id.rb_twenty_four)
                }
                DataStoreConstants.TimeFormat.FORMAT_12 -> {
                    selectedTimeFormatId.postValue(R.id.rb_twelve)
                }
                DataStoreConstants.TimeFormat.SYS_DEF -> {
                    selectedTimeFormatId.postValue(R.id.rb_time_format_system_default)
                }
            }
        }
    }

    private val combinedTasks = combine(
        chosenTimeFormatLiveTask,
        chosenThemeLiveTask,
        chosenLanguageLiveTask
    )

    init {
        viewModelScope.launch {
            combinedTasks.run()
        }
    }

    fun onPrivacyClick() {
        _onPrivacyClick.value = Event(Unit)
    }

    fun onFeedBackClick() {
        _onFeedBackClick.value = Event(Unit)
    }

    fun onRateUsClick() {
        _onRateUsClick.value = Event(Unit)
    }

    fun onSetVersionCode(code: String) {
        _versionCode.value = code
    }
}