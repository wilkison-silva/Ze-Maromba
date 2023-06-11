package br.com.zemaromba.core_domain.model

data class Set(
    val quantity: Int,
    val repetitions: Int,
    val exercise: Exercise,
    val weight: Double,
    val observation: String,
    val completed: Boolean,
    val restingTime: Double
)