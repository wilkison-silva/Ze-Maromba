package br.com.zemaromba.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import br.com.zemaromba.domain.model.Set
import br.com.zemaromba.domain.model.Training

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
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "training_id")
    var id: Long = 0,
    @ColumnInfo(name = "training_plan_id", index = true)
    val trainingPlanId: Long,
    @ColumnInfo(name = "name")
    val name: String,
) {
    fun toTraining(sets: List<Set>): Training {
        return Training(
            id = this.id,
            trainingPlanId = this.trainingPlanId,
            name = this.name,
            sets = sets
        )
    }
}