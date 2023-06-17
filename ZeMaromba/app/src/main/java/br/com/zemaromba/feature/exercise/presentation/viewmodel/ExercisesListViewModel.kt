package br.com.zemaromba.feature.exercise.presentation.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.zemaromba.R
import br.com.zemaromba.common.extensions.toExerciseView
import br.com.zemaromba.core_ui.components.search_bar.SearchBarState
import br.com.zemaromba.feature.exercise.domain.repository.ExercisesRepository
import br.com.zemaromba.feature.exercise.presentation.model.ExerciseView
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ExercisesListViewModel @Inject constructor(
    private val exercisesRepository: ExercisesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ExercisesListState())
    val state = _state.asStateFlow()

    private var originalList = listOf<ExerciseView>()

    init {
        viewModelScope.launch {
            exercisesRepository.getExercisesWithMuscles().collectLatest { exercises ->
                _state.update {
                    it.copy(exercisesList = exercises.map { exercise ->
                        exercise.toExerciseView()
                    })
                }
                originalList = _state.value.exercisesList.map { it.copy() }
                if (_state.value.isInSearchMode) {
                    onEvent(event = ExercisesListEvents.OnSearchExercise(_state.value.searchBarState.text))
                }
            }
        }
    }

    fun onEvent(event: ExercisesListEvents) {
        when (event) {
            is ExercisesListEvents.OnFavoriteExercise -> {
                viewModelScope.launch {
                    exercisesRepository.updateExerciseFavoriteField(
                        exerciseId = event.exerciseId,
                        isFavorite = event.favoriteIcon != R.drawable.ic_star_filled
                    )
                }
            }

            is ExercisesListEvents.OnSearchExercise -> {
                if (event.exerciseName.isBlank()) {
                    _state.update {
                        it.copy(
                            exercisesList = originalList,
                            searchBarState = it.searchBarState.copy(text = ""),
                            isInSearchMode = false
                        )
                    }
                    return
                }
                _state.update {
                    it.copy(
                        searchBarState = it.searchBarState.copy(text = event.exerciseName),
                        isInSearchMode = true
                    )
                }
                val filteredExercises = originalList.filter {
                    it.name.contains(event.exerciseName)
                }
                _state.update {
                    it.copy(exercisesList = filteredExercises)
                }
            }

            is ExercisesListEvents.OnFilterChange -> {
                val chipIndex = event.chipIndex
                val newFilters = _state.value.exerciseFilters
                    .map {
                        it.copy(isSelected = false)
                    }
                    .toMutableList().apply {
                    this[chipIndex] = this[chipIndex].copy(isSelected = !this[chipIndex].isSelected)
                }
                _state.update { it.copy(exerciseFilters = newFilters) }
            }
        }
    }
}

sealed class ExercisesListEvents {
    data class OnFavoriteExercise(
        val exerciseId: Long,
        val favoriteIcon: Int
    ) : ExercisesListEvents()

    data class OnSearchExercise(val exerciseName: String) : ExercisesListEvents()

    data class OnFilterChange(val chipIndex: Int) : ExercisesListEvents()
}

data class ExercisesListState(
    val exercisesList: List<ExerciseView> = listOf(),
    val isInSearchMode: Boolean = false,
    val searchBarState: SearchBarState = SearchBarState(hint = R.string.hint_searchbar_exercise),
    val exerciseFilters: List<ExerciseFilter> = listOf(
        ExerciseFilter(text = R.string.filter_item_all, isSelected = true),
        ExerciseFilter(text = R.string.filter_item_muscle_group, isSelected = false),
        ExerciseFilter(text = R.string.filter_item_favorite, isSelected = false)
    )
)

data class ExerciseFilter(
    @StringRes val text: Int,
    val isSelected: Boolean
)