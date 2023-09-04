package br.com.zemaromba.data.repository

import br.com.zemaromba.common.extensions.orZero
import br.com.zemaromba.data.sources.local.database.dao.ExerciseDao
import br.com.zemaromba.data.model.ExerciseEntity
import br.com.zemaromba.domain.model.Exercise
import br.com.zemaromba.domain.model.MuscleGroup
import br.com.zemaromba.domain.repository.ExercisesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

class ExercisesRepositoryImpl @Inject constructor(
    private val exerciseDao: ExerciseDao
) : ExercisesRepository {

    override fun getExercisesWithMuscles(): Flow<List<Exercise>> = callbackFlow {
        exerciseDao.getExercisesWithMuscleGroups().collect {
            val exercises = it.map { exerciseAndMusclesMap ->
                exerciseAndMusclesMap
                    .key
                    .toExercise(exercisesAndMuscleGroup = exerciseAndMusclesMap.value)
            }
            trySend(exercises)
        }
    }

    override fun getExerciseWithMuscles(exerciseId: Long): Flow<Exercise> = flow {
         emit(exerciseDao.getExerciseWithMuscleGroups(exerciseId = exerciseId)
            .map { exerciseAndMusclesMap ->
                exerciseAndMusclesMap.key.toExercise(exercisesAndMuscleGroup = exerciseAndMusclesMap.value)
            }.first())
    }

    override suspend fun createExercise(
        id: Long?,
        name: String,
        muscleGroupList: List<MuscleGroup>,
        urlLink: String?,
        videoId: String?,
        isEditable: Boolean
    ) {
        if (id.orZero() == 0L) {
            exerciseDao.insertExerciseWithMuscleGroupRef(
                exerciseEntity = ExerciseEntity(
                    id = id.orZero(),
                    name = name,
                    isFavorite = false,
                    urlLink = urlLink,
                    videoId = videoId,
                    isEditable = isEditable
                ),
                muscleGroupList = muscleGroupList
            )
        } else {
            exerciseDao.updateExerciseWithMuscleGroupRef(
                exerciseEntity = ExerciseEntity(
                    id = id.orZero(),
                    name = name,
                    isFavorite = false,
                    urlLink = urlLink,
                    videoId = videoId,
                    isEditable = isEditable
                ),
                muscleGroupList = muscleGroupList
            )
        }
    }

    override fun deleteExercise(exerciseId: Long): Flow<Boolean> = flow {
        val result = exerciseDao.deleteById(exerciseId)
        emit(result == 1)
    }

    override suspend fun updateExerciseFavoriteField(exerciseId: Long, isFavorite: Boolean) {
        exerciseDao.updateFavoriteField(
            exerciseId = exerciseId,
            favorite = isFavorite
        )
    }
}