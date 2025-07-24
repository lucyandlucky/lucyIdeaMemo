package com.example.lucyideamemo.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.lucyideamemo.App
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val THEME_PREFERENCES = "THEME_PREFERENCES"
private val Context.themePreferences by preferencesDataStore(name = THEME_PREFERENCES)

object SettingsPreferences {

    private object PreferencesKeys {
        val FIRST_LAUNCH = booleanPreferencesKey("first_launch")
    }

    private val themePreferences = App.instance.themePreferences

    val fitstLaunch = themePreferences.getBoolean(PreferencesKeys.FIRST_LAUNCH, true)

    private suspend fun <T> updatePreferences(key: Preferences.Key<T>, value: T) {
        themePreferences.edit { preferences ->
            preferences[key] = value
        }
    }

    suspend fun changeFirstLaunch(value: Boolean) {
        updatePreferences(PreferencesKeys.FIRST_LAUNCH, value)

    }
}

fun DataStore<Preferences>.getBoolean(
    key: Preferences.Key<Boolean>,
    defaultValue: Boolean
): Flow<Boolean> {
    return this.data.map { preferences ->
        preferences[key] ?: defaultValue
    }
}