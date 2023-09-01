package br.com.zemaromba.presentation.training_plan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.zemaromba.domain.repository.SetRepository
import br.com.zemaromba.domain.repository.TrainingRepository
import br.com.zemaromba.presentation.training_plan.screen.state.SetListState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SetListViewModel @Inject constructor(
    private val trainingRepository: TrainingRepository,
    private val setRepository: SetRepository
) : ViewModel() {
    private val _state = MutableStateFlow(SetListState())
    val state = _state.asStateFlow()

    fun getTraining(trainingId: Long) {
        trainingRepository
            .getTrainingById(id = trainingId)
            .onEach { training ->
                _state.update {
                    it.copy(trainingName = training.name)
                }
            }.launchIn(viewModelScope)
    }

    fun retrieveSets(trainingId: Long) {
        if (trainingId > 0) {
            setRepository
                .getSetsByTrainingId(trainingId = trainingId)
                .onEach { sets ->
                    _state.update {
                        val setsView = sets.map { set ->
                            set.toSetView()
                        }
                        it.copy(setListView = setsView)
                    }
                }.launchIn(viewModelScope)
        }
    }

    fun completeSet(setId: Long, isCompleted: Boolean) {
        viewModelScope.launch {
            setRepository.completeSet(
                setId = setId,
                isCompleted = isCompleted
            )
        }
    }

    fun showListOptionsBottomSheet(setId: Long) {
        val setView = _state.value.setListView.find {
            it.id == setId
        }
        _state.update {
            it.copy(
                selectedSet = setView,
                showListOptionsBottomSheet = true
            )
        }
    }

    fun hideListOptionsBottomSheet() {
        _state.update {
            it.copy(
                selectedSet = null,
                showListOptionsBottomSheet = false
            )
        }
    }

}