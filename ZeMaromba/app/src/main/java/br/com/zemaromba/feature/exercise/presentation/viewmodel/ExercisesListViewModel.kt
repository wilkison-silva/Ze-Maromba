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
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
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

    private var searchBarJob: Job? = null

    private val searchBarDebounce = 900.milliseconds

    init {
        viewModelScope.launch {
            exercisesRepository.getExercisesWithMuscles().collectLatest { exercises ->
                _state.update {
                    it.copy(exercisesList = exercises.map { exercise ->
                        exercise.toExerciseView()
                    })
                }
                applyFilters()
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
                updateSearchBar(searchBarText = event.exerciseName)

                searchBarJob?.cancel()
                searchBarJob = viewModelScope.launch {
                    delay(duration = searchBarDebounce)
                    applyFilters()
                }

            }

            is ExercisesListEvents.OnFilterChange -> {
                updateExerciseChipFilter(chipIndex = event.chipIndex)
                applyFilters()
            }
        }
    }

    private fun updateSearchBar(searchBarText: String) {
        _state.update {
            it.copy(
                searchBarState = it.searchBarState.copy(text = searchBarText)
            )
        }
    }

    private fun updateExerciseChipFilter(chipIndex: Int) {
        val chipFilters: MutableList<ExerciseFilterChip>
        if (chipIndex == 0) {
            chipFilters = _state.value.exerciseFilters
                .map { it.copy(isSelected = false) }
                .toMutableList().apply { this[0].isSelected = true }
        } else {
            chipFilters = _state.value.exerciseFilters
                .map { it.copy() }
                .toMutableList().apply {
                    this[0].isSelected = false
                    if (this.filter { it.isSelected }.size == 1) {
                        this[chipIndex].isSelected = true
                    } else {
                        this[chipIndex].isSelected = !this[chipIndex].isSelected
                    }
                }
        }
        _state.update { it.copy(exerciseFilters = chipFilters) }
    }

    private fun applyFilters() {
        viewModelScope.launch {
            exercisesRepository.getExercisesWithMuscles().collectLatest { exercises ->
                var filteredList = exercises.map { exercise -> exercise.toExerciseView() }
                _state.value.exerciseFilters.filter { it.isSelected }.forEach {
                    when (it.text) {
                        R.string.filter_item_all -> {
                            return@forEach
                        }

                        R.string.filter_item_muscle_group -> {
                            return@forEach
                        }

                        R.string.filter_item_favorite -> {
                            filteredList = filteredList.filter { exerciseView ->
                                exerciseView.favoriteIcon == R.drawable.ic_star_filled
                            }
                        }
                    }
                }
                val searchBarText = _state.value.searchBarState.text
                if (searchBarText.isNotBlank()) {
                    filteredList = filteredList.filter {
                        it.name.contains(searchBarText, ignoreCase = true)
                    }
                }
                _state.update {
                    it.copy(exercisesList = filteredList)
                }
                cancel()
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
    val searchBarState: SearchBarState = SearchBarState(hint = R.string.hint_searchbar_exercise),
    val exerciseFilters: List<ExerciseFilterChip> = listOf(
        ExerciseFilterChip(text = R.string.filter_item_all, isSelected = true),
        ExerciseFilterChip(text = R.string.filter_item_muscle_group, isSelected = false),
        ExerciseFilterChip(text = R.string.filter_item_favorite, isSelected = false)
    )
)

data class ExerciseFilterChip(
    @StringRes val text: Int,
    var isSelected: Boolean
)