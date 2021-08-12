package comtest.dandeliontest.todotest.data.datesources.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import comtest.dandeliontest.todotest.data.utils.DataStoreConstants
import comtest.dandeliontest.todotest.data.utils.DataStoreConstants.LANGUAGE_KEY
import comtest.dandeliontest.todotest.data.utils.DataStoreConstants.THEME_KEY
import comtest.dandeliontest.todotest.data.utils.DataStoreConstants.TIME_FORMAT_KEY
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class SettingsLocalDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : SettingsLocalDataSource {

    private val Context.themeDataStore: DataStore<Preferences> by preferencesDataStore(
        name = DataStoreConstants.THEME_DATA_STORE_NAME
    )

    private val Context.languageDataStore: DataStore<Preferences> by preferencesDataStore(
        name = DataStoreConstants.LANGUAGE_DATA_STORE_NAME
    )

    override fun getCurrentTheme(): Flow<Int> = context.themeDataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map { preference ->
            preference[THEME_KEY] ?: 2
        }

    override suspend fun setCurrentTheme(themeCode: Int) {
        context.themeDataStore.edit { pref ->
            pref[THEME_KEY] = themeCode
        }
    }

    override fun getLanguage(): Flow<Int> = context.languageDataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map { preference ->
            preference[DataStoreConstants.LANGUAGE_KEY] ?: 0
        }

    override suspend fun setLanguage(languageCode: Int) {
        context.languageDataStore.edit { pref ->
            pref[LANGUAGE_KEY] = languageCode
        }
    }

    override fun getTimeFormat(): Flow<Int> = context.themeDataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map { preference ->
            preference[DataStoreConstants.TIME_FORMAT_KEY] ?: 0
        }

    override suspend fun setTimeFormat(timeFormatCode: Int) {
        context.themeDataStore.edit { pref ->
            pref[TIME_FORMAT_KEY] = timeFormatCode
        }
    }
}