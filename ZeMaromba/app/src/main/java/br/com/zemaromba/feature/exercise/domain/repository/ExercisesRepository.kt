package br.com.zemaromba.feature.exercise.domain.repository

import br.com.zemaromba.core_domain.model.Exercise
import kotlinx.coroutines.flow.Flow

interface ExercisesRepository {

    fun getExercisesWithMuscles(): Flow<List<Exercise>>

}