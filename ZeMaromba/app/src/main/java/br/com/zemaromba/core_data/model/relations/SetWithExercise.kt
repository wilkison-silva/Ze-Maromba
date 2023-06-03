package br.com.zemaromba.core_data.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import br.com.zemaromba.core_data.model.ExerciseEntity
import br.com.zemaromba.core_data.model.SetEntity

data class SetWithExercise(
    @Embedded
    val exerciseEntity: ExerciseEntity,
    @Relation(
        parentColumn = "exercise_id",
        entityColumn = "exercise_id"
    )
    val setEntity: SetEntity
)
