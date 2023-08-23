package br.com.zemaromba.domain.model

import br.com.zemaromba.presentation.model.TrainingSummaryView

data class Training(
    val id: Long,
    val trainingPlanId: Long,
    val name: String,
    val sets: List<Set>
) {
    fun toTrainingSummaryView(): TrainingSummaryView {

        val totalSets = this.sets.size
        val totalCompleted = this.sets.count { it.completed }
        val percentageDone: Float = if (totalSets > 0) {
            (totalCompleted.toFloat() / totalSets.toFloat()) * 100
        } else {
            0.0f
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
            percentageDone = percentageDone.toInt()
        )
    }
}