package br.com.zemaromba.feature.onboarding.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.zemaromba.core_data.local.database.dao.ExerciseDao
import br.com.zemaromba.core_domain.datastore.UserDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class GetStartedViewModel @Inject constructor(
    private val userDataStore: UserDataStore,
    private val exerciseDao: ExerciseDao
) : ViewModel() {

    private val _state = MutableStateFlow(GetStartedState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update { it.copy(loadingScreen = true) }
            userDataStore.getName().collectLatest { userName ->
                delay(1000)
                if (userName.isNotBlank()) {
                    _state.update {
                        it.copy(
                            userNameIsValid = true,
                            userName = userName
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            userNameIsValid = false,
                            loadingScreen = false
                        )
                    }
                }
            }
        }
        viewModelScope.launch {
            Log.i("Testando", "aqui!")
            exerciseDao.getExercisesWithMuscleGroups().collect {
                it.forEach {
                    Log.i("Testando", "${it.exercise} -> ${it.muscleGroupList}")
                }
            }
        }
    }
}

data class GetStartedState(
    val userNameIsValid: Boolean = false,
    val loadingScreen: Boolean = false,
    val userName: String = ""
)