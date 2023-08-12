package br.com.zemaromba.presentation.exercises.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.zemaromba.R
import br.com.zemaromba.common.extensions.orFalse
import br.com.zemaromba.domain.model.ExerciseFilter
import br.com.zemaromba.domain.model.MuscleGroup
import br.com.zemaromba.domain.repository.ExercisesRepository
import br.com.zemaromba.presentation.components.search_bar.SearchBarState
import br.com.zemaromba.presentation.exercises.screen.state.MuscleGroupCheckBoxState
import br.com.zemaromba.presentation.model.ExerciseView
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ExercisesListViewModel @Inject constructor(
    private val exercisesRepository: ExercisesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ExercisesListState())
    val state = _state.asStateFlow()

    private var searchBarJob: Job? = null

    private val searchBarDebounce = 500.milliseconds

    init {
        exercisesRepository
            .getExercisesWithMuscles()
            .onEach { exercises ->
                _state.update {
                    it.copy(exercisesList = exercises.map { exercise ->
                        exercise.toExerciseView()
                    })
                }
                applyFilters()
            }.launchIn(viewModelScope)
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
                updateExerciseChipFilter(exerciseFilter = event.exerciseFilter)
            }

            is ExercisesListEvents.OnFilterBySelectedMuscleGroups -> {

                val hasSelectedSomeMuscleGroup = _state.value.muscleGroupCheckBoxStates.any {
                    it.isSelected
                }
                val hasSelectedOtherFilter = _state.value.exerciseFilters.any {
                    it.type != ExerciseFilter.MUSCLE_GROUP && it.isSelected
                }


                _state.update {
                    it.copy(
                        exerciseFilters = it.exerciseFilters.map { exerciseFilterChip ->
                            if (exerciseFilterChip.type == ExerciseFilter.MUSCLE_GROUP) {
                                exerciseFilterChip.copy(isSelected = hasSelectedSomeMuscleGroup)
                            } else {
                                exerciseFilterChip
                            }
                        },
                        showMuscleGroupBottomSheet = false
                    )
                }


                if (!hasSelectedSomeMuscleGroup && !hasSelectedOtherFilter) {
                    updateExerciseChipFilter(exerciseFilter = ExerciseFilter.ALL)
                } else {
                    applyFilters()
                }

            }

            is ExercisesListEvents.OnMuscleGroupSelection -> {
                val checkBoxesState = _state.value.muscleGroupCheckBoxStates.toMutableList().apply {
                    this[event.id] = this[event.id].copy(isSelected = event.isSelected)
                }
                _state.update { it.copy(muscleGroupCheckBoxStates = checkBoxesState) }
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

    private fun updateExerciseChipFilter(exerciseFilter: ExerciseFilter) {
        val chipFilters: List<ExerciseFilterChip>
        when (exerciseFilter) {
            ExerciseFilter.ALL -> {
                chipFilters = listOf(
                    ExerciseFilterChip(type = ExerciseFilter.ALL, isSelected = true),
                    ExerciseFilterChip(type = ExerciseFilter.MUSCLE_GROUP, isSelected = false),
                    ExerciseFilterChip(type = ExerciseFilter.FAVORITE, isSelected = false)
                )
                _state.update { exerciseListState ->
                    exerciseListState.copy(
                        exerciseFilters = chipFilters,
                        showMuscleGroupBottomSheet = false,
                        muscleGroupCheckBoxStates = exerciseListState
                            .muscleGroupCheckBoxStates
                            .map { muscleGroupCheckBox ->
                                muscleGroupCheckBox.copy(
                                    isSelected = false
                                )
                            }
                    )
                }
            }

            ExerciseFilter.MUSCLE_GROUP -> {
                val isFavoriteSelected = _state.value.exerciseFilters.find {
                    it.type == ExerciseFilter.FAVORITE
                }?.isSelected.orFalse()
                chipFilters = listOf(
                    ExerciseFilterChip(type = ExerciseFilter.ALL, isSelected = false),
                    ExerciseFilterChip(type = ExerciseFilter.MUSCLE_GROUP, isSelected = true),
                    ExerciseFilterChip(
                        type = ExerciseFilter.FAVORITE,
                        isSelected = isFavoriteSelected
                    )
                )
                _state.update {
                    it.copy(
                        exerciseFilters = chipFilters,
                        showMuscleGroupBottomSheet = true
                    )
                }
            }

            ExerciseFilter.FAVORITE -> {
                val isMuscleGroupFilterSelected = _state.value.exerciseFilters.find {
                    it.type == ExerciseFilter.MUSCLE_GROUP
                }?.isSelected.orFalse()
                val isFavoriteSelected = _state.value.exerciseFilters.find {
                    it.type == ExerciseFilter.FAVORITE
                }?.isSelected.orFalse()
                chipFilters = listOf(
                    ExerciseFilterChip(
                        type = ExerciseFilter.ALL,
                        isSelected = (!isMuscleGroupFilterSelected && isFavoriteSelected)
                    ),
                    ExerciseFilterChip(
                        type = ExerciseFilter.MUSCLE_GROUP,
                        isSelected = isMuscleGroupFilterSelected
                    ),
                    ExerciseFilterChip(
                        type = ExerciseFilter.FAVORITE,
                        isSelected = !isFavoriteSelected
                    )
                )
                _state.update {
                    it.copy(
                        exerciseFilters = chipFilters,
                        showMuscleGroupBottomSheet = false
                    )
                }
            }
        }
        applyFilters()
    }

    private fun applyFilters() {
        exercisesRepository
            .getExercisesWithMuscles()
            .onCompletion {
                currentCoroutineContext().cancel()
            }
            .onEach { exercises ->
                var filteredList = exercises.map { exercise -> exercise.toExerciseView() }
                _state.value.exerciseFilters.filter { it.isSelected }
                    .forEach { exerciseFilterChip ->
                        when (exerciseFilterChip.type) {
                            ExerciseFilter.ALL -> {
                                return@forEach
                            }

                            ExerciseFilter.MUSCLE_GROUP -> {
                                val selectedMuscleGroups = _state.value.muscleGroupCheckBoxStates
                                    .filter { muscleGroupCheckBox ->
                                        muscleGroupCheckBox.isSelected
                                    }.map { it.nameRes }
                                if (selectedMuscleGroups.isNotEmpty()) {
                                    filteredList = filteredList.filter { exerciseView ->
                                        exerciseView.muscleGroups.intersect(selectedMuscleGroups.toSet())
                                            .isNotEmpty()
                                    }
                                }
                            }

                            ExerciseFilter.FAVORITE -> {
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
                    it.copy(
                        exercisesList = filteredList,
                        showNothingFound = filteredList.isEmpty()
                    )
                }
            }.launchIn(viewModelScope)
    }
}

sealed class ExercisesListEvents {
    data class OnFavoriteExercise(
        val exerciseId: Long,
        val favoriteIcon: Int
    ) : ExercisesListEvents()

    data class OnSearchExercise(val exerciseName: String) : ExercisesListEvents()

    data class OnFilterChange(val exerciseFilter: ExerciseFilter) : ExercisesListEvents()

    object OnFilterBySelectedMuscleGroups : ExercisesListEvents()

    data class OnMuscleGroupSelection(val id: Int, val isSelected: Boolean) :
        ExercisesListEvents()
}

data class ExercisesListState(
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
    val showNothingFound: Boolean = false
)

data class ExerciseFilterChip(
    var isSelected: Boolean,
    val type: ExerciseFilter
)
