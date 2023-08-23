package br.com.zemaromba.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.zemaromba.domain.model.TrainingPlan

@Entity(tableName = "TrainingPlan")
data class TrainingPlanEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "training_plan_id")
    var id: Long = 0,
    @ColumnInfo(name = "name")
    val name: String
) {
    fun toTrainingPlan(): TrainingPlan {
        return TrainingPlan(
            id = this.id,
            name = this.name,
            trainings = emptyList()
        )
    }
}