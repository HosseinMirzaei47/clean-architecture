package comtest.dandeliontest.todotest.data.utils

import androidx.datastore.preferences.core.intPreferencesKey

object DataStoreConstants {
    val THEME_KEY = intPreferencesKey("THEME")
    val LANGUAGE_KEY = intPreferencesKey("LANGUAGE")
    val TIME_FORMAT_KEY = intPreferencesKey("TIME_FORMAT")
    val THEME_DATA_STORE_NAME = "TODO_THEME_DATA_STORE"
    val LANGUAGE_DATA_STORE_NAME = "TODO_LANGUAGE_DATA_STORE"
    val CHANGELOG_DATA_STORE_NAME = "TODO_CHANGELOG_DATA_STORE.pb"
    val STATE_DATA_STORE = "TODO_STATE_DATA_STORE"
}