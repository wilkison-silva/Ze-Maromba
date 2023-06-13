package br.com.zemaromba.core_data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Exercise")
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "exercise_id")
    val id: Long = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "favorite")
    val favorite: Boolean
)