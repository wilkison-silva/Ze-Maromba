package br.com.zemaromba.feature.exercise.domain.model

import br.com.zemaromba.R

enum class ExerciseFilter(val nameRes: Int) {
    ALL(nameRes = R.string.filter_item_all),
    MUSCLE_GROUP(nameRes = R.string.filter_item_muscle_group),
    FAVORITE(nameRes = R.string.filter_item_favorite)
}