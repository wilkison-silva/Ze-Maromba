package br.com.zemaromba.feature.exercise.data.repository

import br.com.zemaromba.common.extensions.toExercise
import br.com.zemaromba.core_data.local.database.dao.ExerciseAndMuscleDao
import br.com.zemaromba.core_data.local.database.dao.ExerciseDao
import br.com.zemaromba.core_data.model.ExerciseAndMuscleGroupEntity
import br.com.zemaromba.core_data.model.ExerciseEntity
import br.com.zemaromba.core_domain.model.Exercise
import br.com.zemaromba.core_domain.model.MuscleGroup
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

    override suspend fun createExercise(name: String, muscleGroupList: List<MuscleGroup>) {
        val id = exerciseDao.insert(
            exerciseEntity = ExerciseEntity(
                name = name,
                favorite = false
            )
        )
        muscleGroupList.forEach {
            exerciseAndMuscleDao.insert(ExerciseAndMuscleGroupEntity(
                exerciseId = id,
                muscleName = it.name
            ))
        }
    }
}