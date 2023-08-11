package br.com.zemaromba.feature.training_plan.presentation.model

import br.com.zemaromba.feature.exercise.presentation.model.ExerciseView

data class SetView(
    val id: Long,
    val quantity: Int,
    val repetitions: Int,
    val exerciseView: ExerciseView,
    val weight: Double,
    val observation: String,
    val completed: Boolean,
    val restingTime: Double
)
