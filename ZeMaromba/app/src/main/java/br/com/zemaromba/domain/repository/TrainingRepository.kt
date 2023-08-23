package br.com.zemaromba.domain.repository

import br.com.zemaromba.domain.model.Training
import kotlinx.coroutines.flow.Flow

interface TrainingRepository {

    fun getTrainingById(id: Long): Flow<Training>

    suspend fun createTraining(id: Long?, name: String, trainingPlanId: Long)

    suspend fun deleteTraining(id: Long): Boolean
}