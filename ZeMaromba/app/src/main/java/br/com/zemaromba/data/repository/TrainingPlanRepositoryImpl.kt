package br.com.zemaromba.data.repository

import br.com.zemaromba.common.extensions.orZero
import br.com.zemaromba.data.model.TrainingPlanEntity
import br.com.zemaromba.data.sources.local.database.dao.ExerciseDao
import br.com.zemaromba.data.sources.local.database.dao.SetDao
import br.com.zemaromba.data.sources.local.database.dao.TrainingPlanDao
import br.com.zemaromba.domain.model.Training
import br.com.zemaromba.domain.model.TrainingPlan
import br.com.zemaromba.domain.repository.TrainingPlanRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class TrainingPlanRepositoryImpl(
    private val trainingPlanDao: TrainingPlanDao,
    private val setDao: SetDao,
    private val exerciseDao: ExerciseDao
) : TrainingPlanRepository {

    override fun getAllTrainingPlans(): Flow<List<TrainingPlan>> = callbackFlow {
        trainingPlanDao.getAllTrainingPlans().collectLatest {
            val trainingPlan = it.map { trainingPlanEntity ->
                trainingPlanEntity.toTrainingPlan()
            }
            trySend(trainingPlan)
        }
    }

    override  fun getTrainingPlanById(id: Long): Flow<TrainingPlan> = flow {
        val trainingPlan = trainingPlanDao
            .getTrainingPlanById(trainingPlanId = id)
            .toTrainingPlan()
        emit(trainingPlan)
    }

    override suspend fun createTrainingPlan(id: Long?, name: String){
        if (id.orZero() == 0L) {
            trainingPlanDao.insert(
                trainingPlanEntity = TrainingPlanEntity(
                    id = id.orZero(),
                    name = name
                )
            )
        } else {
            trainingPlanDao.update(
                trainingPlanEntity = TrainingPlanEntity(
                    id = id.orZero(),
                    name = name
                )
            )
        }
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
            val setsEntityList = setDao
                .getSetByTrainingId(trainingId = trainingEntity.id)
                .first()

            val sets = setsEntityList.map {
                val exercise =
                    exerciseDao
                        .getExerciseWithMuscleGroups(exerciseId = it.exerciseId)
                        .map { exerciseAndMusclesMap ->
                            exerciseAndMusclesMap
                                .key
                                .toExercise(exercisesAndMuscleGroup = exerciseAndMusclesMap.value)
                        }.first()
                it.toSet(exercise)
            }
            val training = trainingEntity.toTraining(sets)
            trainingList.add(training)
        }
        emit(trainingList)
    }
}