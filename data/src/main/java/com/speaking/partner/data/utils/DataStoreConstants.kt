package com.speaking.partner.data.utils

import androidx.datastore.preferences.core.intPreferencesKey

object DataStoreConstants {
    val THEME_KEY = intPreferencesKey("THEME")
    val LANGUAGE_KEY = intPreferencesKey("LANGUAGE")
    val TIME_FORMAT_KEY = intPreferencesKey("TIME_FORMAT")
    const val THEME_DATA_STORE_NAME = "CORE_THEME_DATA_STORE"
    const val LANGUAGE_DATA_STORE_NAME = "CORE_LANGUAGE_DATA_STORE"
    const val CHANGELOG_DATA_STORE_NAME = "CORE_CHANGELOG_DATA_STORE.pb"
    const val STATE_DATA_STORE = "CORE_STATE_DATA_STORE"
}