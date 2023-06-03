package br.com.zemaromba.core_data.model.cross_references

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    primaryKeys = ["exercise_id",  "muscle_group_id"],
    tableName = "ExerciseAndMuscleGroup"
)
data class ExerciseAndMuscleGroupCrossRefEntity(
    @ColumnInfo(name = "exercise_id")
    val exerciseId: Long,
    @ColumnInfo(name = "muscle_group_id", index = true)
    val muscleId: Long
)