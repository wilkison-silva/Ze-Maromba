package br.com.zemaromba.common.extensions

import br.com.zemaromba.core_data.model.SetEntity
import br.com.zemaromba.core_domain.model.Exercise
import br.com.zemaromba.core_domain.model.Set

fun SetEntity.toSet(exercise: Exercise): Set {
    return Set(
        id = this.id,
        quantity = this.quantity,
        repetitions = this.repetitions,
        exercise = exercise,
        weight = this.weight,
        observation = this.observation,
        completed = this.completed,
        restingTime = this.restingTime
    )
}