package br.com.zemaromba.presentation.sets.screen.state

import br.com.zemaromba.presentation.model.ExerciseView

data class CreateExerciseState(
    val selectedExercise: ExerciseView? = null,
    val progressBarInitial: Float = 0.0f,
    val progressBarTarget: Float = 0.33f,
    val seriesValue: String = "",
    val repetitionsValue: String = "",
    val weightValue: String = "",
    val restingTimeValue: String = "",
    val observation: String = "",
    val trainingId: Long = 0,
)