package br.com.zemaromba.feature.training_plan.domain.repository

import br.com.zemaromba.core_domain.model.Training
import br.com.zemaromba.core_domain.model.TrainingPlan
import kotlinx.coroutines.flow.Flow

interface TrainingPlanRepository {

    fun getAllTrainingPlans(): Flow<List<TrainingPlan>>

    suspend fun getTrainingPlanById(id: Long): TrainingPlan

    suspend fun createTrainingPlan(id: Long?, name: String)

    suspend fun deleteTrainingPlan(id: Long): Boolean

    fun getTrainingsByTrainingPlanId(trainingPlanId: Long): Flow<List<Training>>
}