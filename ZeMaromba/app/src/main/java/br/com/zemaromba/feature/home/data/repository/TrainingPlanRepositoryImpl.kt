package br.com.zemaromba.feature.home.data.repository

import br.com.zemaromba.common.extensions.toTrainingPlan
import br.com.zemaromba.core_data.local.database.dao.TrainingPlanDao
import br.com.zemaromba.core_domain.model.TrainingPlan
import br.com.zemaromba.feature.home.domain.repository.TrainingPlanRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TrainingPlanRepositoryImpl(
    private val trainingPlanDao: TrainingPlanDao
) : TrainingPlanRepository {

    override fun getAllTrainingPlans(): Flow<List<TrainingPlan>> {
        return trainingPlanDao.getTrainingPlan().map {
            it.map { trainingPlanEntity ->
                trainingPlanEntity.toTrainingPlan()
            }
        }
    }

}