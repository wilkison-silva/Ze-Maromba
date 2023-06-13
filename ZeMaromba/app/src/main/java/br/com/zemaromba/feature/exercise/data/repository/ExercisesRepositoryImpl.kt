package br.com.zemaromba.feature.exercise.data.repository

import br.com.zemaromba.core_data.local.database.dao.ExerciseAndMuscleDao
import br.com.zemaromba.core_data.local.database.dao.ExerciseDao
import br.com.zemaromba.core_domain.model.Exercise
import br.com.zemaromba.feature.exercise.domain.repository.ExercisesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExercisesRepositoryImpl @Inject constructor(
    private val exerciseDao: ExerciseDao,
    private val exerciseAndMuscleDao: ExerciseAndMuscleDao
) : ExercisesRepository {

    override fun getExercises(): Flow<List<Exercise>> {
        return exerciseDao.getAll().map {
            it.map {
                Exercise(
                    id = it.id,
                    name = it.name,
                    muscleGroup = listOf(),
                    favorite = it.favorite
                )
            }
        }
    }

    override fun getMusclesByExerciseId(exerciseId: Long): Flow<List<String>> {
        return exerciseAndMuscleDao
            .getMusclesByExerciseId(exerciseId = exerciseId)
            .map {
                it.map {
                    it.muscleName
                }
            }
    }
}