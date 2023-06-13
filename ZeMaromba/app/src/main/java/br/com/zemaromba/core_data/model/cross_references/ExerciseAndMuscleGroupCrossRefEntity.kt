package br.com.zemaromba.core_data.model.cross_references

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import br.com.zemaromba.core_data.model.ExerciseEntity

@Entity(
    tableName = "ExerciseAndMuscleGroup",
    primaryKeys = ["exercise_id", "muscle_name"],
    foreignKeys = [
        ForeignKey(
            entity = ExerciseEntity::class,
            parentColumns = arrayOf("exercise_id"),
            childColumns = arrayOf("exercise_id"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ExerciseAndMuscleGroupCrossRefEntity(
    @ColumnInfo(name = "exercise_id")
    val exerciseId: Long,
    @ColumnInfo(name = "muscle_name", index = true)
    val muscleName: String
)