package comtest.dandeliontest.todotest

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.*
import com.part.livetaskcore.usecases.asLiveTask
import comtest.dandeliontest.todotest.domain.coroutineUtils.ApplicationScope
import comtest.dandeliontest.todotest.domain.coroutineUtils.MainDispatcher
import comtest.dandeliontest.todotest.domain.usecases.settings.GetChosenThemeUseCase
import comtest.dandeliontest.todotest.domain.usecases.settings.GetLanguageUseCase
import comtest.dandeliontest.todotest.domain.utils.DataStoreConstants.Languages.ENGLISH
import comtest.dandeliontest.todotest.domain.utils.DataStoreConstants.Languages.PERSIAN
import comtest.dandeliontest.todotest.domain.utils.DataStoreConstants.Theme.LIGHT
import comtest.dandeliontest.todotest.domain.utils.DataStoreConstants.Theme.NIGHT
import comtest.dandeliontest.todotest.domain.utils.DataStoreConstants.Theme.SYS_DEF
import comtest.dandeliontest.todotest.shared.resource.onSuccess
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