package br.com.zemaromba.presentation.features.training_plan.model

import br.com.zemaromba.presentation.features.exercises.model.ExerciseView

data class SetView(
    val id: Long,
    val quantity: Int,
    val repetitions: Int,
    val exerciseView: ExerciseView,
    val weight: Int,
    val observation: String,
    val completed: Boolean,
    val restingTime: Int
)
