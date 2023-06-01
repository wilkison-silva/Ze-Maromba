package br.com.zemaromba.core_data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "muscle_groups")
data class MuscleGroupData(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @ColumnInfo(name = "exercise_id")
    val exerciseId: Long,
    @ColumnInfo(name = "primary_muscle_group")
    val primaryMuscleGroup: String,
    @ColumnInfo(name = "secondary_muscle_group")
    val secondaryMuscleGroup: String
)