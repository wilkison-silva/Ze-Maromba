package br.com.zemaromba.feature.training_plan.data.repository

import br.com.zemaromba.common.extensions.orZero
import br.com.zemaromba.common.extensions.toTrainingPlan
import br.com.zemaromba.core_data.local.database.dao.TrainingPlanDao
import br.com.zemaromba.core_data.model.TrainingPlanEntity
import br.com.zemaromba.core_domain.model.TrainingPlan
import br.com.zemaromba.feature.training_plan.domain.repository.TrainingPlanRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class TrainingPlanRepositoryImpl(
    private val trainingPlanDao: TrainingPlanDao
) : TrainingPlanRepository {

    override fun getAllTrainingPlans(): Flow<List<TrainingPlan>> {
        return trainingPlanDao.getAllTrainingPlans().map {
            it.map { trainingPlanEntity ->
                trainingPlanEntity.toTrainingPlan()
            }
        }
    }

    override suspend fun getTrainingPlanById(id: Long): TrainingPlan {
        return trainingPlanDao.getTrainingPlanById(trainingPlanId = id).map {
            it.toTrainingPlan()
        }.first()
    }

    override suspend fun createTrainingPlan(id: Long?, name: String): Long {
        return trainingPlanDao.insert(
            trainingPlanEntity = TrainingPlanEntity(
                id = id.orZero(),
                name = name
            )
        )
    }

    override suspend fun deleteTrainingPlan(id: Long): Boolean {
        val result = trainingPlanDao.deleteById(trainingPlanId = id)
        return result == 1
    }

}