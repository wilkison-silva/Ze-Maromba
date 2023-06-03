package br.com.zemaromba.feature.onboarding.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.zemaromba.core_data.local.dao.ExerciseDao
import br.com.zemaromba.core_data.local.dao.MuscleGroupDao
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
//            val exerciseId = exerciseDao.insert(ExerciseData(name = "Biceps alternado"))
//            val muscleGroupId = muscleGroupDao.insert(
//                MuscleGroupData(name = MuscleGroup.BICEPS.name)
//            )
//            exerciseDao.insertExerciseWithMuscle(
//                ExerciseMuscleGroupCrossRef(
//                    exerciseId = exerciseId,
//                    muscleId = 1,
//                    primaryOrSecondary = "PRIMARY"
//                )
//            )
//            val list1 = exerciseDao.getAll().collectLatest {
//
//            }
//
//            val list2: List<ExercisesWithMuscles> = exerciseDao.getExercisesWithMuscleGroups()
//            Log.i("Testando", "getAll ->: $list1")
//            Log.i("Testando", "exercises with muscles ->: $list2")
        }
    }

}