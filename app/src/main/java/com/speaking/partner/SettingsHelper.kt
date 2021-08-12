package com.speaking.partner

import android.content.Context
import android.content.res.Configuration
import com.speaking.partner.domain.coroutineUtils.ApplicationScope
import com.speaking.partner.domain.coroutineUtils.MainDispatcher
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import java.util.*
import javax.inject.Inject

@ApplicationScope
class SettingsHelper @Inject constructor(
    @ApplicationContext private val context: Context,
    @MainDispatcher private val dispatcher: CoroutineDispatcher,
) {

    private val config = Configuration(context.resources.configuration)

    private fun setConfigLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        config.setLocale(locale)
    }

}