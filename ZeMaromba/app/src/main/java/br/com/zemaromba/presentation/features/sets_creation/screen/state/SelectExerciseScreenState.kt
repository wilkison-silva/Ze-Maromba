package br.com.zemaromba.presentation.features.sets_creation.screen.state

import br.com.zemaromba.R
import br.com.zemaromba.domain.model.ExerciseFilter
import br.com.zemaromba.domain.model.MuscleGroup
import br.com.zemaromba.presentation.components.search_bar.SearchBarState
import br.com.zemaromba.presentation.features.exercises.screen.state.MuscleGroupCheckBoxState
import br.com.zemaromba.presentation.features.exercises.viewmodel.ExerciseFilterChip
import br.com.zemaromba.presentation.features.exercises.model.ExerciseView

data class SelectExerciseScreenState(
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
    val scrollPosition: Int = 0,
    val isLoading: Boolean = true
)