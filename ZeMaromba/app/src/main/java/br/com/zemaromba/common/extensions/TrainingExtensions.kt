package br.com.zemaromba.common.extensions

import br.com.zemaromba.core_data.model.TrainingEntity
import br.com.zemaromba.core_domain.model.MuscleGroup
import br.com.zemaromba.core_domain.model.Set
import br.com.zemaromba.core_domain.model.Training
import br.com.zemaromba.feature.training_plan.presentation.model.TrainingSummaryView

fun TrainingEntity.toTraining(sets: List<Set>?): Training {
    return Training(
        id = this.id,
        name = this.name,
        sets = sets ?: emptyList()
    )
}

fun Training.toTrainingSummaryView(): TrainingSummaryView {

    val totalSets = this.sets.size
    val totalCompleted = this.sets.count { it.completed }
    val percentageDone = if (totalSets > 0) {
        totalCompleted / totalSets
    } else {
        0
    }

    var muscleGroups = mutableListOf<MuscleGroup>()
    this.sets.forEach { set ->
        set.exercise.muscleGroupList.forEach { muscleGroup ->
            muscleGroups.add(muscleGroup)
        }
    }
    muscleGroups = muscleGroups.toSet().toMutableList()

    return TrainingSummaryView(
        id = this.id,
        name = this.name,
        exercisesQuantity = this.sets.size,
        muscleGroups = muscleGroups,
        percentageDone = percentageDone
    )
}