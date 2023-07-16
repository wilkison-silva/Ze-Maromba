package br.com.zemaromba.common.extensions

import br.com.zemaromba.R
import br.com.zemaromba.core_data.model.ExerciseAndMuscleGroupEntity
import br.com.zemaromba.core_data.model.ExerciseDTO
import br.com.zemaromba.core_data.model.ExerciseEntity
import br.com.zemaromba.core_domain.model.Exercise
import br.com.zemaromba.core_domain.model.MuscleGroup
import br.com.zemaromba.feature.exercise.presentation.model.ExerciseView

fun ExerciseDTO.toExerciseEntity(): ExerciseEntity {
    return ExerciseEntity(
        id = this.id,
        name = this.name,
        favorite = false,
        urlLink = this.videoUrl,
        videoId = this.videoId
    )
}

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
        muscleGroupList = muscleGroupList,
        urlLink = this.urlLink,
        videoId = this.videoId
    )
}

fun Exercise.toExerciseView(): ExerciseView {
    return ExerciseView(
        id = this.id,
        name = this.name,
        favoriteIcon = if (this.favorite) R.drawable.ic_star_filled else R.drawable.ic_star_border,
        muscleGroups = this.muscleGroupList.map { muscleGroup -> muscleGroup.nameRes },
        urlLink = this.urlLink,
        videoId = this.videoId
    )
}