package br.com.zemaromba.feature.exercise.data.repository

import br.com.zemaromba.core_data.local.database.dao.ExerciseDao
import br.com.zemaromba.core_domain.model.Exercise
import br.com.zemaromba.feature.exercise.domain.repository.ExercisesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExercisesRepositoryImpl @Inject constructor(
    private val exerciseDao: ExerciseDao
) : ExercisesRepository {

    override fun getExercises(): Flow<List<Exercise>> {
        return exerciseDao.getExercisesWithMuscleGroups().map {
            it.map { exerciseAndMusclesMap ->
                Exercise(
                    id = exerciseAndMusclesMap.key.id,
                    name = exerciseAndMusclesMap.key.name,
                    favorite = exerciseAndMusclesMap.key.favorite,
                    muscleGroup = exerciseAndMusclesMap.value.map { exerciseAndMuscleGroupEntity ->
                        exerciseAndMuscleGroupEntity.muscleName
                    }
                )
            }
        }
    }
}