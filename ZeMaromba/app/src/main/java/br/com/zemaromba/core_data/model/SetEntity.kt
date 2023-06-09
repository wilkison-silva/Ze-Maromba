package br.com.zemaromba.core_data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Set",
    foreignKeys = [
        ForeignKey(
            entity = ExerciseEntity::class,
            parentColumns = arrayOf("exercise_id"),
            childColumns = arrayOf("exercise_id"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TrainingEntity::class,
            parentColumns = arrayOf("training_id"),
            childColumns = arrayOf("training_id"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SetEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @ColumnInfo(name = "exercise_id", index = true)
    val exerciseId: Long,
    @ColumnInfo(name = "training_id", index = true)
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
