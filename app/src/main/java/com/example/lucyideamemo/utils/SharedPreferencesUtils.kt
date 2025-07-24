package com.example.lucyideamemo.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.lucyideamemo.App
import com.example.lucyideamemo.ui.page.SortTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val SHARED_PREFERENCES_STORE_NAME = "SHARED_PREFERENCES"
private val Context.sharedDataStore by preferencesDataStore(name = SHARED_PREFERENCES_STORE_NAME)

object SharedPreferencesUtils {
    private object PreferenceKeys {
        val SORT_TIME = stringPreferencesKey("sort_time")
    }

    private val sharedPreferences = App.instance.sharedDataStore

    val sortTime: Flow<SortTime> =
        sharedPreferences.getEnum(PreferenceKeys.SORT_TIME, SortTime.UPDATE_TIME_DESC)
}


inline fun <reified T : Enum<T>> DataStore<Preferences>.getEnum(
    key: Preferences.Key<String>,
    defaultValue: T
): Flow<T> {
    return this.data.map { preferences ->
        preferences[key]?.let {
            try {
                enumValueOf<T>(it)
            } catch (e: IllegalArgumentException) {
                defaultValue
            }
        } ?: defaultValue
    }
}