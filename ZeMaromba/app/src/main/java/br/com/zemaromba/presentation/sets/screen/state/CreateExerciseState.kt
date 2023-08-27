package br.com.zemaromba.presentation.sets.screen.state

import br.com.zemaromba.R
import br.com.zemaromba.domain.model.ExerciseFilter
import br.com.zemaromba.domain.model.MuscleGroup
import br.com.zemaromba.presentation.components.search_bar.SearchBarState
import br.com.zemaromba.presentation.exercises.screen.state.MuscleGroupCheckBoxState
import br.com.zemaromba.presentation.exercises.viewmodel.ExerciseFilterChip
import br.com.zemaromba.presentation.model.ExerciseView

data class CreateExerciseState(
    val exercisesList: List<ExerciseView> = listOf(),
    val searchBarState: SearchBarState = SearchBarState(hint = R.string.hint_searchbar_exercise),
    val exerciseFilters: List<ExerciseFilterChip> = listOf(
        ExerciseFilterChip(type = ExerciseFilter.ALL, isSelected = true),
        ExerciseFilterChip(type = ExerciseFilter.MUSCLE_GROUP, isSelected = false),
        ExerciseFilterChip(type = ExerciseFilter.FAVORITE, isSelected = false)
    ),
    val showMuscleGroupBottomSheet: Boolean = false,
    val muscleGroupCheckBoxStates: List<MuscleGroupCheckBoxState> = MuscleGroup.values().map {
        MuscleGroupCheckBoxState(
            nameRes = it.nameRes,
            isSelected = false
        )
    },
    val showNothingFound: Boolean = false,
    val selectedExercise: ExerciseView? = null,
    val progressBarInitial: Float = 0.0f,
    val progressBarTarget: Float = 0.33f,
    val seriesValue: String = "",
    val repetitionsValue: String = "",
    val weightValue: String = "",
    val restingTimeValue: String = "",
    val observation: String = ""
) {
    val isAllTextInputsNotEmpty = seriesValue.isNotBlank() &&
            repetitionsValue.isNotBlank() &&
            weightValue.isNotBlank() &&
            restingTimeValue.isNotBlank()
}