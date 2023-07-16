package br.com.zemaromba.feature.exercise.domain.repository

import br.com.zemaromba.core_domain.model.Exercise
import br.com.zemaromba.core_domain.model.MuscleGroup
import br.com.zemaromba.feature.exercise.domain.model.ExerciseFilter
import kotlinx.coroutines.flow.Flow

interface ExercisesRepository {

    fun getExercisesWithMuscles(): Flow<List<Exercise>>

    fun getExercisesWithMuscle(
        exerciseName: String,
        filter: ExerciseFilter
    ): Flow<List<Exercise>>

    suspend fun getExerciseWithMuscles(exerciseId: Long): Exercise

    suspend fun createExercise(
        id: Long?,
        name: String,
        muscleGroupList: List<MuscleGroup>,
        urlLink: String?
    )

    suspend fun deleteExercise(exerciseId: Long): Boolean

    suspend fun updateExerciseFavoriteField(exerciseId: Long, isFavorite: Boolean)


}