package br.com.zemaromba.domain.repository

import br.com.zemaromba.domain.model.Set
import kotlinx.coroutines.flow.Flow

interface SetRepository {

    fun getSetsByTrainingId(trainingId: Long): Flow<List<Set>>

    suspend fun completeSet(setId: Long, isCompleted: Boolean)

    suspend fun createSet(
        id: Long,
        exerciseId: Long,
        trainingId: Long,
        quantity: Int,
        repetitions: Int,
        weight: Int,
        observation: String,
        completed: Boolean,
        restingTime: Int
    )

    suspend fun deleteSet(id: Long): Boolean

}