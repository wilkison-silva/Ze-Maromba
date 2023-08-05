package br.com.zemaromba.core_data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import br.com.zemaromba.core_domain.datastore.UserDataStore
import br.com.zemaromba.core_domain.datastore.UserDataStore.Companion.PREFERENCES_USER_NAME
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class UserDataStoreImpl @Inject constructor(
    private val context: Context
) : UserDataStore {

    override fun getName(): Flow<String> = flow {
        val preferences = context.dataStore.data.first()
        emit(preferences[PREFERENCES_USER_NAME].orEmpty())
    }

    override suspend fun saveName(name: String) {
        context.dataStore.edit {
            it[PREFERENCES_USER_NAME] = name
        }
    }

}