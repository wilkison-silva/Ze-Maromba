package br.com.zemaromba.feature.exercise.domain.repository

import br.com.zemaromba.core_domain.model.Exercise
import br.com.zemaromba.core_domain.model.MuscleGroup
import kotlinx.coroutines.flow.Flow

interface ExercisesRepository {

    fun getExercisesWithMuscles(): Flow<List<Exercise>>

    suspend fun createExercise(name: String, muscleGroupList: List<MuscleGroup>)

}