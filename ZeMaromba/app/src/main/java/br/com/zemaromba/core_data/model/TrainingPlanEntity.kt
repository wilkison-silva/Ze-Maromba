package br.com.zemaromba.core_data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TrainingPlan")
data class TrainingPlanEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "training_plan_id")
    var id: Long = 0,
    @ColumnInfo(name = "name")
    val name: String
)