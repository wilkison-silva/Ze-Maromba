package br.com.zemaromba.data.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import br.com.zemaromba.data.model.TrainingEntity
import br.com.zemaromba.data.model.TrainingPlanEntity

data class TrainingPlanWithTrainings(
    @Embedded
    val trainingPlan: TrainingPlanEntity,
    @Relation(
        parentColumn = "training_plan_id",
        entityColumn = "training_plan_id"
    )
    val trainingList: List<TrainingEntity>
)
