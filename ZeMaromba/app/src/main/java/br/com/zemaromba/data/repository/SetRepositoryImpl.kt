package br.com.zemaromba.data.repository

import br.com.zemaromba.common.extensions.orZero
import br.com.zemaromba.data.model.SetEntity
import br.com.zemaromba.data.sources.local.database.dao.ExerciseDao
import br.com.zemaromba.data.sources.local.database.dao.SetDao
import br.com.zemaromba.domain.model.Set
import br.com.zemaromba.domain.repository.SetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest

class SetRepositoryImpl(
    private val setDao: SetDao,
    private val exerciseDao: ExerciseDao
) : SetRepository {

    override fun getSetsByTrainingId(trainingId: Long): Flow<List<Set>> = callbackFlow {
        setDao
            .getSetByTrainingId(trainingId = trainingId)
            .collectLatest { setEntityList ->
                val sets = setEntityList.map { setEntity ->
                    val exercise =
                        exerciseDao
                            .getExerciseWithMuscleGroups(exerciseId = setEntity.exerciseId)
                            .map { exerciseAndMusclesMap ->
                                exerciseAndMusclesMap
                                    .key
                                    .toExercise(exercisesAndMuscleGroup = exerciseAndMusclesMap.value)
                            }.first()
                    setEntity.toSet(exercise)
                }
                trySend(sets)
            }
    }

    override suspend fun completeSet(setId: Long, isCompleted: Boolean) {
        setDao.completeSet(
            setId = setId,
            completed = !isCompleted
        )
    }

    override suspend fun createSet(
        id: Long,
        exerciseId: Long,
        trainingId: Long,
        quantity: Int,
        repetitions: Int,
        weight: Int,
        observation: String,
        completed: Boolean,
        restingTime: Int
    ) {
        if (id.orZero() == 0L) {
            setDao.insert(
                setEntity = SetEntity(
                    id = id,
                    exerciseId = exerciseId,
                    trainingId = trainingId,
                    repetitions = repetitions,
                    quantity = quantity,
                    weight = weight,
                    observation = observation,
                    completed = completed,
                    restingTime = restingTime
                )
            )
        } else {
            setDao.update(
                setEntity = SetEntity(
                    id = id,
                    exerciseId = exerciseId,
                    trainingId = trainingId,
                    repetitions = repetitions,
                    quantity = quantity,
                    weight = weight,
                    observation = observation,
                    completed = completed,
                    restingTime = restingTime
                )
            )
        }
    }

    override suspend fun deleteSet(id: Long): Boolean {
        val result = setDao.deleteById(setId = id)
        return result == 1
    }

    override suspend fun getSetById(id: Long): Set {
        val setEntity = setDao.getSet(setId = id).first()
        val exercise = exerciseDao
            .getExerciseWithMuscleGroups(exerciseId = setEntity.exerciseId)
            .map { exerciseAndMusclesMap ->
                exerciseAndMusclesMap
                    .key
                    .toExercise(exercisesAndMuscleGroup = exerciseAndMusclesMap.value)
            }.first()
        return setEntity.toSet(exercise)
    }
}