package br.com.zemaromba.core_data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Set")
data class SetEntity(
    @PrimaryKey
    var id: Long = 0,
    @ColumnInfo(name = "exercise_id")
    val exerciseId: Long,
    @ColumnInfo(name = "training_id")
    val training_id: Long,
    @ColumnInfo(name = "quantity")
    val quantity: Int,
    @ColumnInfo(name = "repetitions")
    val repetitions: Int,
    @ColumnInfo(name = "weight")
    val weight: Double,
    @ColumnInfo(name = "observation")
    val observation: String,
    @ColumnInfo(name = "completed")
    val completed: Boolean,
    @ColumnInfo(name = "resting_time")
    val restingTime: Double
)
