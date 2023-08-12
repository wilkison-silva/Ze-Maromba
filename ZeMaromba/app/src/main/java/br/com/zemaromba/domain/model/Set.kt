package br.com.zemaromba.domain.model

data class Set(
    val id: Long,
    val quantity: Int,
    val repetitions: Int,
    val exercise: Exercise,
    val weight: Double,
    val observation: String,
    val completed: Boolean,
    val restingTime: Double
)