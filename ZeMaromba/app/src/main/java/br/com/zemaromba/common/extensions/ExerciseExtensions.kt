package br.com.zemaromba.common.extensions

import br.com.zemaromba.R
import br.com.zemaromba.core_data.model.ExerciseAndMuscleGroupEntity
import br.com.zemaromba.core_data.model.ExerciseEntity
import br.com.zemaromba.core_domain.model.Exercise
import br.com.zemaromba.core_domain.model.MuscleGroup
import br.com.zemaromba.feature.exercise.presentation.model.ExerciseView

fun ExerciseEntity.toExercise(exercisesAndMuscleGroup: List<ExerciseAndMuscleGroupEntity>): Exercise {
    val muscleGroupList = mutableListOf<MuscleGroup>()
    exercisesAndMuscleGroup.forEach { exerciseIdAndMuscleGroupName ->
        MuscleGroup.values().find {
            it.name == exerciseIdAndMuscleGroupName.muscleName
        }?.let {
            muscleGroupList.add(it)
        }
    }
    return Exercise(
        id = this.id,
        name = this.name,
        favorite = this.favorite,
        muscleGroup = muscleGroupList
    )
}

fun Exercise.toExerciseView(): ExerciseView {
    return ExerciseView(
        id = this.id,
        name = this.name,
        favoriteIcon = if (this.favorite) R.drawable.ic_star_filled else R.drawable.ic_star_border,
        muscleGroups = this.muscleGroup.map { muscleGroup ->
            when (muscleGroup) {
                MuscleGroup.CHEST -> R.string.peitoral
                MuscleGroup.DORSAL -> R.string.dorsal
                MuscleGroup.DELTOID -> R.string.deltoides
                MuscleGroup.TRAPEZIUS -> R.string.trapezius
                MuscleGroup.BICEPS -> R.string.biceps
                MuscleGroup.TRICEPS -> R.string.triceps
                MuscleGroup.FOREARM -> R.string.forearm
                MuscleGroup.QUADRICEPS -> R.string.quadriceps
                MuscleGroup.HAMSTRINGS -> R.string.hamstrings
                MuscleGroup.ADDUCTORS -> R.string.adductors
                MuscleGroup.ABDUCTORS -> R.string.abductors
                MuscleGroup.GLUTES -> R.string.glutes
                MuscleGroup.CALVES -> R.string.calves
                MuscleGroup.ABDOMEN -> R.string.abdomen
                MuscleGroup.LUMBAR -> R.string.lumbar
            }
        }
    )
}