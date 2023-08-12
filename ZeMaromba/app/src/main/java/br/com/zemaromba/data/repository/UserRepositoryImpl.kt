package br.com.zemaromba.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import br.com.zemaromba.data.sources.local.datastore.dataStore
import br.com.zemaromba.domain.repository.UserRepository
import br.com.zemaromba.domain.repository.UserRepository.Companion.PREFERENCES_USER_NAME
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest

class UserRepositoryImpl @Inject constructor(
    private val context: Context
) : UserRepository {

    override fun getName(): Flow<String> = callbackFlow {
        context.dataStore.data.collectLatest { preferences ->
            trySend(preferences[PREFERENCES_USER_NAME].orEmpty())
        }
    }

    override suspend fun saveName(name: String) {
        context.dataStore.edit {
            it[PREFERENCES_USER_NAME] = name
        }
    }

}