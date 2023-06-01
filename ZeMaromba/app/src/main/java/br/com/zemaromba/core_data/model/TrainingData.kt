package br.com.zemaromba.core_data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trainings")
data class TrainingData(
    @PrimaryKey
    var id: Long = 0,
    @ColumnInfo(name = "name")
    val name: String,
)