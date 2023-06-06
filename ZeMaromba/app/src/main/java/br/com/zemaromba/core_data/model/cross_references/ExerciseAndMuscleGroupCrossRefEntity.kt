package br.com.zemaromba.core_data.model.cross_references

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import br.com.zemaromba.core_data.model.ExerciseEntity
import br.com.zemaromba.core_data.model.MuscleGroupEntity

@Entity(
    tableName = "ExerciseAndMuscleGroup",
    primaryKeys = ["exercise_id", "muscle_group_id"],
    foreignKeys = [
        ForeignKey(
            entity = ExerciseEntity::class,
            parentColumns = arrayOf("exercise_id"),
            childColumns = arrayOf("exercise_id"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = MuscleGroupEntity::class,
            parentColumns = arrayOf("muscle_group_id"),
            childColumns = arrayOf("muscle_group_id"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ExerciseAndMuscleGroupCrossRefEntity(
    @ColumnInfo(name = "exercise_id")
    val exerciseId: Long,
    @ColumnInfo(name = "muscle_group_id", index = true)
    val muscleId: Long
)