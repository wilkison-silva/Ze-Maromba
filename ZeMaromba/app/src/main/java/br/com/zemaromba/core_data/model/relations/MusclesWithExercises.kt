package br.com.zemaromba.core_data.model.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import br.com.zemaromba.core_data.model.ExerciseEntity
import br.com.zemaromba.core_data.model.MuscleGroupEntity
import br.com.zemaromba.core_data.model.cross_references.ExerciseAndMuscleGroupCrossRefEntity

data class MusclesWithExercises(
    @Embedded
    val muscleGroupEntity: MuscleGroupEntity,
    @Relation(
        parentColumn = "muscle_group_id",
        entityColumn = "exercise_id",
        associateBy = Junction(ExerciseAndMuscleGroupCrossRefEntity::class)
    )
    val exerciseData: List<ExerciseEntity>
)
