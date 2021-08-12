package com.speaking.partner

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.*
import com.part.livetaskcore.usecases.asLiveTask
import com.speaking.partner.domain.coroutineUtils.ApplicationScope
import com.speaking.partner.domain.coroutineUtils.MainDispatcher
import com.speaking.partner.domain.usecases.settings.GetChosenThemeUseCase
import com.speaking.partner.domain.usecases.settings.GetLanguageUseCase
import com.speaking.partner.domain.utils.DataStoreConstants.Languages.ENGLISH
import com.speaking.partner.domain.utils.DataStoreConstants.Languages.PERSIAN
import com.speaking.partner.domain.utils.DataStoreConstants.Theme.LIGHT
import com.speaking.partner.domain.utils.DataStoreConstants.Theme.NIGHT
import com.speaking.partner.domain.utils.DataStoreConstants.Theme.SYS_DEF
import com.speaking.partner.shared.resource.onSuccess
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*
import javax.inject.Inject

@ApplicationScope
class SettingsHelper @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getChosenThemeUseCase: GetChosenThemeUseCase,
    private val getLanguageUseCase: GetLanguageUseCase,
    @MainDispatcher private val dispatcher: CoroutineDispatcher,
) {

    private val config = Configuration(context.resources.configuration)

    private val themeLiveTask = getChosenThemeUseCase.asLiveTask {
        onSuccess<Int> {
            when (it) {
                NIGHT -> {
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
                }
                LIGHT -> {
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
                }
                SYS_DEF -> {
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
        }
    }

    init {
        CoroutineScope(dispatcher).launch {
            themeLiveTask(Unit)
        }
    }

    private fun setConfigLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        config.setLocale(locale)
    }

    fun getModifiedContext(): Context = runBlocking {
        getLanguageUseCase(Unit).first().onSuccess {
            when (it) {
                PERSIAN -> {
                    setConfigLocale("fa")
                }
                ENGLISH -> {
                    setConfigLocale("en")
                }
            }
        }
        getChosenThemeUseCase(Unit).first().onSuccess {
            when (it) {
                NIGHT -> {
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
                }
                LIGHT -> {
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
                }
                SYS_DEF -> {
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
        }

        context.createConfigurationContext(config)
    }
}