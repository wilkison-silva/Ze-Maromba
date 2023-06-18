package br.com.zemaromba.feature.exercise.data.repository

import br.com.zemaromba.common.extensions.orZero
import br.com.zemaromba.common.extensions.toExercise
import br.com.zemaromba.core_data.local.database.dao.ExerciseAndMuscleDao
import br.com.zemaromba.core_data.local.database.dao.ExerciseDao
import br.com.zemaromba.core_data.model.ExerciseAndMuscleGroupEntity
import br.com.zemaromba.core_data.model.ExerciseEntity
import br.com.zemaromba.core_domain.model.Exercise
import br.com.zemaromba.core_domain.model.MuscleGroup
import br.com.zemaromba.feature.exercise.domain.model.ExerciseFilter
import br.com.zemaromba.feature.exercise.domain.repository.ExercisesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExercisesRepositoryImpl @Inject constructor(
    private val exerciseDao: ExerciseDao,
    private val exerciseAndMuscleDao: ExerciseAndMuscleDao
) : ExercisesRepository {

    override fun getExercisesWithMuscles(): Flow<List<Exercise>> {
        return exerciseDao.getExercisesWithMuscleGroups().map {
            it.map { exerciseAndMusclesMap ->
                exerciseAndMusclesMap
                    .key
                    .toExercise(exercisesAndMuscleGroup = exerciseAndMusclesMap.value)
            }
        }
    }

    override fun getExercisesWithMuscle(
        exerciseName: String,
        filter: ExerciseFilter
    ): Flow<List<Exercise>> {
        when (filter) {
            ExerciseFilter.ALL -> {
                return exerciseDao
                    .getExercisesWithMuscleGroupsByName(exerciseName = exerciseName)
                    .map {
                        it.map { exerciseAndMusclesMap ->
                            exerciseAndMusclesMap
                                .key
                                .toExercise(exercisesAndMuscleGroup = exerciseAndMusclesMap.value)
                        }
                    }
            }

            ExerciseFilter.MUSCLE_GROUP -> {
                return exerciseDao.getExercisesWithMuscleGroups().map {
                    it.map { exerciseAndMusclesMap ->
                        exerciseAndMusclesMap
                            .key
                            .toExercise(exercisesAndMuscleGroup = exerciseAndMusclesMap.value)
                    }
                }
            }

            ExerciseFilter.FAVORITE -> {
                return exerciseDao
                    .getExercisesWithMuscleGroupsByNameAndFavoriteStatus(exerciseName = exerciseName)
                    .map {
                        it.map { exerciseAndMusclesMap ->
                            exerciseAndMusclesMap
                                .key
                                .toExercise(exercisesAndMuscleGroup = exerciseAndMusclesMap.value)
                        }
                    }
            }
        }
    }

    override suspend fun getExerciseWithMuscles(exerciseId: Long): Exercise {
        return exerciseDao.getExerciseWithMuscleGroups(exerciseId = exerciseId)
            .map { exerciseAndMusclesMap ->
                exerciseAndMusclesMap.key.toExercise(exercisesAndMuscleGroup = exerciseAndMusclesMap.value)
            }.first()
    }

    override suspend fun createExercise(
        id: Long?,
        name: String,
        muscleGroupList: List<MuscleGroup>
    ) {
        val exerciseId = exerciseDao.insert(
            exerciseEntity = ExerciseEntity(
                id = id.orZero(),
                name = name,
                favorite = false
            )
        )
        muscleGroupList.forEach {
            exerciseAndMuscleDao.insert(
                ExerciseAndMuscleGroupEntity(
                    exerciseId = exerciseId,
                    muscleName = it.name
                )
            )
        }
    }

    override suspend fun deleteExercise(exerciseId: Long): Boolean {
        val result = exerciseDao.deleteById(exerciseId)
        return result == 1
    }

    override suspend fun updateExerciseFavoriteField(exerciseId: Long, isFavorite: Boolean) {
        exerciseDao.updateFavoriteField(
            exerciseId = exerciseId,
            favorite = isFavorite
        )
    }
}