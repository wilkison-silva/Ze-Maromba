package br.com.zemaromba.core_data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Training",
    foreignKeys = [
        ForeignKey(
            entity = TrainingPlanEntity::class,
            parentColumns = arrayOf("training_plan_id"),
            childColumns = arrayOf("training_plan_id"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TrainingEntity(
    @PrimaryKey
    @ColumnInfo(name = "training_id")
    var id: Long = 0,
    @ColumnInfo(name = "training_plan_id", index = true)
    val trainingPlanId: Long,
    @ColumnInfo(name = "name")
    val name: String,
)