package br.com.zemaromba.domain.repository

import br.com.zemaromba.domain.model.Training
import br.com.zemaromba.domain.model.TrainingPlan
import kotlinx.coroutines.flow.Flow

interface TrainingPlanRepository {

    fun getAllTrainingPlans(): Flow<List<TrainingPlan>>

    fun getTrainingPlanById(id: Long): Flow<TrainingPlan>

    suspend fun createTrainingPlan(id: Long?, name: String)

    suspend fun deleteTrainingPlan(id: Long): Boolean

    fun getTrainingsByTrainingPlanId(trainingPlanId: Long): Flow<List<Training>>
}