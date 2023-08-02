package br.com.zemaromba.feature.training_plan.data.repository

import android.util.Log
import br.com.zemaromba.common.extensions.orZero
import br.com.zemaromba.common.extensions.toExercise
import br.com.zemaromba.common.extensions.toSet
import br.com.zemaromba.common.extensions.toTraining
import br.com.zemaromba.common.extensions.toTrainingPlan
import br.com.zemaromba.core_data.local.database.dao.ExerciseDao
import br.com.zemaromba.core_data.local.database.dao.SetDao
import br.com.zemaromba.core_data.local.database.dao.TrainingPlanDao
import br.com.zemaromba.core_data.model.TrainingPlanEntity
import br.com.zemaromba.core_data.model.relations.TrainingPlanWithTrainings
import br.com.zemaromba.core_domain.model.Training
import br.com.zemaromba.core_domain.model.TrainingPlan
import br.com.zemaromba.feature.training_plan.domain.repository.TrainingPlanRepository
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.launch

class TrainingPlanRepositoryImpl(
    private val trainingPlanDao: TrainingPlanDao,
    private val setDao: SetDao,
    private val exerciseDao: ExerciseDao
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

    override fun getTrainingsByTrainingPlanId(trainingPlanId: Long): Flow<List<Training>> = flow {
        val trainingPlanWithTrainings = trainingPlanDao
            .getTrainingPlanWithTrainings(trainingPlanId)
            .first()

        val trainingList = mutableListOf<Training>()
        trainingPlanWithTrainings.trainingList.forEach { trainingEntity ->
            val setsWithExercises = setDao
                .getSetsWithExerciseByTrainingId(trainingId = trainingEntity.id)
                .first()

            val sets = setsWithExercises.map {
                val exercise =
                    exerciseDao
                        .getExerciseWithMuscleGroups(exerciseId = it.exercise.id)
                        .map { exerciseAndMusclesMap ->
                            exerciseAndMusclesMap
                                .key
                                .toExercise(exercisesAndMuscleGroup = exerciseAndMusclesMap.value)
                        }.first()
                it.set.toSet(exercise)
            }
            val training = trainingEntity.toTraining(sets)
            trainingList.add(training)
        }
        emit(trainingList)
    }
}