package br.com.zemaromba.core_data.model.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import br.com.zemaromba.core_data.model.ExerciseEntity
import br.com.zemaromba.core_data.model.MuscleGroupEntity
import br.com.zemaromba.core_data.model.cross_references.ExerciseAndMuscleGroupCrossRefEntity

data class ExercisesWithMuscles(
    @Embedded
    val exerciseEntity: ExerciseEntity,
    @Relation(
        parentColumn = "exercise_id",
        entityColumn = "muscle_group_id",
        associateBy = Junction(ExerciseAndMuscleGroupCrossRefEntity::class)
    )
    val muscleGroupData: List<MuscleGroupEntity>
)
