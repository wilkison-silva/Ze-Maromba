package br.com.zemaromba.core_domain.datastore

import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow

interface UserDataStore {

    suspend fun saveName(name: String)

    fun getName(): Flow<String>

    companion object {
        val PREFERENCES_USER_NAME = stringPreferencesKey("user_name")
    }
}