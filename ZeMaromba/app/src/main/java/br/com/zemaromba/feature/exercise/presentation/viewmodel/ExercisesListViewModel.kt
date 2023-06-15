package br.com.zemaromba.feature.exercise.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.zemaromba.common.extensions.toExerciseView
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

    init {
        viewModelScope.launch {
            launch {
                exercisesRepository.getExercisesWithMuscles().collectLatest { exercises ->
                    _state.update {
                        it.copy(exercisesList = exercises.map { exercise ->
                            exercise.toExerciseView()
                        })
                    }
                }
            }
        }
    }
}

data class ExercisesListState(
    val exercisesList: List<ExerciseView> = listOf(),
)