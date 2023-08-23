package br.com.zemaromba.domain.model

import br.com.zemaromba.presentation.model.SetView

data class Set(
    val id: Long,
    val trainingId: Long,
    val quantity: Int,
    val repetitions: Int,
    val exercise: Exercise,
    val weight: Double,
    val observation: String,
    val completed: Boolean,
    val restingTime: Double
) {
    fun toSetView(): SetView {
        return SetView(
            id = this.id,
            quantity = this.quantity,
            repetitions = this.repetitions,
            exerciseView = this.exercise.toExerciseView(),
            weight = this.weight,
            observation = this.observation,
            completed = this.completed,
            restingTime = this.restingTime
        )
    }
}