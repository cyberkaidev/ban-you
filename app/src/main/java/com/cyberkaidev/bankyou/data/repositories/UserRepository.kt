package com.cyberkaidev.bankyou.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map

private val addressPreference = stringPreferencesKey("user_data")

class UserRepository(
    private val dataStore: DataStore<Preferences>
) {
    val address = dataStore.data.map {
        it[addressPreference] ?: ""
    }

    suspend fun setAddress(value: String) {
        dataStore.edit { preferences ->
            preferences[addressPreference] = value
        }
    }
}