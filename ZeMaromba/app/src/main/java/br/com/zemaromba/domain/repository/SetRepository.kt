package br.com.zemaromba.domain.repository

import br.com.zemaromba.domain.model.Set
import kotlinx.coroutines.flow.Flow

interface SetRepository {

    fun getSetsByTrainingId(trainingId: Long): Flow<List<Set>>

    suspend fun completeSet(setId: Long, isCompleted: Boolean)

}