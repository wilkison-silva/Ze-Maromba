package br.com.zemaromba.feature.training_plan.domain.repository

import br.com.zemaromba.core_domain.model.Training

interface TrainingRepository {

    suspend fun getTrainingById(id: Long): Training

    suspend fun createTraining(id: Long?, name: String, trainingPlanId: Long)

    suspend fun deleteTraining(id: Long): Boolean
}