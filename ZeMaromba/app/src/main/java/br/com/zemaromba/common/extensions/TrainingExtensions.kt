package br.com.zemaromba.common.extensions

import br.com.zemaromba.core_domain.model.MuscleGroup
import br.com.zemaromba.core_domain.model.Training
import br.com.zemaromba.feature.training_plan.presentation.model.TrainingView

fun Training.toTrainingView(): TrainingView {

    val totalSets = this.sets.size
    val totalCompleted = this.sets.count { it.completed }
    val percentageDone = totalCompleted / totalSets

    var muscleGroups = mutableListOf<MuscleGroup>()
    this.sets.forEach { set ->
        set.exercise.muscleGroupList.forEach { muscleGroup ->
            muscleGroups.add(muscleGroup)
        }
    }
    muscleGroups = muscleGroups.toSet().toMutableList()

    return TrainingView(
        id = this.id,
        name = this.name,
        exercisesQuantity = this.sets.size,
        muscleGroups = muscleGroups,
        percentageDone = percentageDone
    )
}