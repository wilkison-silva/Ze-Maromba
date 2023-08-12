package br.com.zemaromba.data.repository

import br.com.zemaromba.common.extensions.orZero
import br.com.zemaromba.data.model.TrainingEntity
import br.com.zemaromba.data.sources.local.database.dao.ExerciseDao
import br.com.zemaromba.data.sources.local.database.dao.SetDao
import br.com.zemaromba.data.sources.local.database.dao.TrainingDao
import br.com.zemaromba.domain.model.Training
import br.com.zemaromba.domain.repository.TrainingRepository
import kotlinx.coroutines.flow.first

class TrainingRepositoryImpl(
    private val trainingDao: TrainingDao,
    private val setDao: SetDao,
    private val exerciseDao: ExerciseDao
) : TrainingRepository {

    override suspend fun getTrainingById(id: Long): Training {
        val trainingEntity = trainingDao.getTrainingById(trainingId = id)
        val setsWithExercises = setDao
            .getSetsWithExerciseByTrainingId(trainingId = trainingEntity.trainingPlanId)
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
        return trainingEntity.toTraining(sets = sets)

    }

    override suspend fun createTraining(id: Long?, name: String, trainingPlanId: Long) {
        if (id.orZero() == 0L) {
            trainingDao.insert(
                trainingEntity = TrainingEntity(
                    id = id.orZero(),
                    name = name,
                    trainingPlanId = trainingPlanId
                )
            )
        } else {
            trainingDao.update(
                trainingEntity = TrainingEntity(
                    id = id.orZero(),
                    name = name,
                    trainingPlanId = trainingPlanId
                )
            )
        }
    }

    override suspend fun deleteTraining(id: Long): Boolean {
        val result = trainingDao.deleteById(trainingId = id)
        return result == 1
    }

}