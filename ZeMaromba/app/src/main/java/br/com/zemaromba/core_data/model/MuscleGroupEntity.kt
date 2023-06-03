package br.com.zemaromba.core_data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MuscleGroup")
data class MuscleGroupEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "muscle_group_id")
    val id: Long = 0,
    @ColumnInfo(name = "name")
    val name: String
)