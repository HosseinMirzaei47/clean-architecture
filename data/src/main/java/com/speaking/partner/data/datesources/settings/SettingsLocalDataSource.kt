package com.speaking.partner.data.datesources.settings

import kotlinx.coroutines.flow.Flow

interface SettingsLocalDataSource {
    fun getCurrentTheme(): Flow<Int>
    suspend fun setCurrentTheme(themeCode: Int)
    fun getLanguage(): Flow<Int>
    suspend fun setLanguage(languageCode: Int)
    fun getTimeFormat(): Flow<Int>
    suspend fun setTimeFormat(timeFormatCode: Int)
}