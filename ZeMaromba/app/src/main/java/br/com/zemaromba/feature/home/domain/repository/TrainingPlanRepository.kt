package br.com.zemaromba.feature.home.domain.repository

import br.com.zemaromba.core_domain.model.TrainingPlan
import kotlinx.coroutines.flow.Flow

interface TrainingPlanRepository {

    fun getAllTrainingPlans(): Flow<List<TrainingPlan>>

}