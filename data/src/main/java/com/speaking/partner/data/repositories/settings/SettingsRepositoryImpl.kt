package com.speaking.partner.data.repositories.settings

import com.speaking.partner.data.datesources.settings.SettingsLocalDataSource
import com.speaking.partner.domain.repositories.settings.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val dataSource: SettingsLocalDataSource
) : SettingsRepository {

    override fun getCurrentTheme(): Flow<Int> = dataSource.getCurrentTheme()

    override suspend fun setCurrentTheme(themeCode: Int) = dataSource.setCurrentTheme(themeCode)

    override fun getLanguage(): Flow<Int> = dataSource.getLanguage()

    override suspend fun setLanguage(languageCode: Int) = dataSource.setLanguage(languageCode)

    override fun getTimeFormat(): Flow<Int> = dataSource.getTimeFormat()

    override suspend fun setTimeFormat(timeFormatCode: Int) =
        dataSource.setTimeFormat(timeFormatCode)
}