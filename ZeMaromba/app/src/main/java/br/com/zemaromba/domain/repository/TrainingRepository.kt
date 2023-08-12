package br.com.zemaromba.domain.repository

import br.com.zemaromba.domain.model.Training

interface TrainingRepository {

    suspend fun getTrainingById(id: Long): Training

    suspend fun createTraining(id: Long?, name: String, trainingPlanId: Long)

    suspend fun deleteTraining(id: Long): Boolean
}