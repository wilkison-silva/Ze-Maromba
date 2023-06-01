package br.com.trainingjourney.feature.onboarding.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.zemaromba.core_data.local.dao.ExerciseDao
import br.com.zemaromba.core_data.local.dao.MuscleGroupDao
import br.com.zemaromba.core_data.model.ExerciseData
import br.com.zemaromba.core_data.model.MuscleGroupData
import br.com.zemaromba.feature.training_origination.domain.model.MuscleGroup
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class TesteViewModel @Inject constructor(
    private val exerciseDao: ExerciseDao,
    private val muscleGroupDao: MuscleGroupDao
) : ViewModel() {

    fun addExercises() {
        viewModelScope.launch {
            val id = exerciseDao.insert(ExerciseData(name = "Biceps"))
            val id2 = muscleGroupDao.insert(
                MuscleGroupData(
                    exerciseId = id,
                    primaryMuscleGroup = MuscleGroup.BICEPS.name,
                    secondaryMuscleGroup = MuscleGroup.FOREARM.name
                )
            )
            Log.i("Testando", "addExercises: $id, $id2")
        }
    }

}