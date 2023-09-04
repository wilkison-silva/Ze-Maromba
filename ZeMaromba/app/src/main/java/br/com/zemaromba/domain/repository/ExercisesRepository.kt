package br.com.zemaromba.domain.repository

import br.com.zemaromba.domain.model.Exercise
import br.com.zemaromba.domain.model.MuscleGroup
import kotlinx.coroutines.flow.Flow

interface ExercisesRepository {

    fun getExercisesWithMuscles(): Flow<List<Exercise>>

    fun getExerciseWithMuscles(exerciseId: Long): Flow<Exercise>

    suspend fun createExercise(
        id: Long?,
        name: String,
        muscleGroupList: List<MuscleGroup>,
        urlLink: String?,
        videoId: String?,
        mayExclude: Boolean,
        isNativeFromApp: Boolean
    )

    fun deleteExercise(exerciseId: Long): Flow<Boolean>

    suspend fun updateExerciseFavoriteField(exerciseId: Long, isFavorite: Boolean)
}