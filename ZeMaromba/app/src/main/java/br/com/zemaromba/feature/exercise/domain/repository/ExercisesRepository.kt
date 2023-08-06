package br.com.zemaromba.feature.exercise.domain.repository

import br.com.zemaromba.core_domain.model.Exercise
import br.com.zemaromba.core_domain.model.MuscleGroup
import kotlinx.coroutines.flow.Flow

interface ExercisesRepository {

    fun getExercisesWithMuscles(): Flow<List<Exercise>>

    fun getExerciseWithMuscles(exerciseId: Long): Flow<Exercise>

    suspend fun createExercise(
        id: Long?,
        name: String,
        muscleGroupList: List<MuscleGroup>,
        urlLink: String?,
        videoId: String?
    )

    fun deleteExercise(exerciseId: Long): Flow<Boolean>

    suspend fun updateExerciseFavoriteField(exerciseId: Long, isFavorite: Boolean)
}