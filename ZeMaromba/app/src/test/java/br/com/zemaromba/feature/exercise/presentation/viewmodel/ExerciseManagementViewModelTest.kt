package br.com.zemaromba.feature.exercise.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.zemaromba.core_domain.model.Exercise
import br.com.zemaromba.core_domain.model.MuscleGroup
import br.com.zemaromba.feature.exercise.domain.repository.ExercisesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ExerciseManagementViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = UnconfinedTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private val exercisesRepository = mockk<ExercisesRepository>()
    private val viewModel = ExerciseManagementViewModel(exercisesRepository)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When user types name, field name must show name typed and flag nameIsBlank must be false`() =
        runTest {
            val name = "Bíceps na polia"
            viewModel.onEvent(event = ExerciseManagementEvents.OnEnterName(exerciseName = name))

            assertEquals(name, viewModel.state.value.name)
            assertEquals(false, viewModel.state.value.nameIsBlank)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When user does not type name and click on save button, flag nameIsBlank must be true`() =
        runTest {
            viewModel.onEvent(event = ExerciseManagementEvents.OnSaveExercise)

            assertEquals("", viewModel.state.value.name)
            assertEquals(true, viewModel.state.value.nameIsBlank)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When user types name, does not select muscle group and click on save button, flag nameIsBlank must be false and showMessageAboutMuscleGroup must be true`() =
        runTest {
            val name = "Bíceps na polia"
            viewModel.onEvent(event = ExerciseManagementEvents.OnEnterName(exerciseName = name))
            viewModel.onEvent(event = ExerciseManagementEvents.OnSaveExercise)

            assertEquals(name, viewModel.state.value.name)
            assertEquals(false, viewModel.state.value.nameIsBlank)
            assertEquals(true, viewModel.state.value.showMessageAboutMuscleGroup)

        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When user types name, select muscle group and click on save button, flag nameIsBlank must be false and showMessageAboutMuscleGroup must be false`() =
        runTest {
            val name = "Bíceps na polia"
            val selectedMuscleGroup = MuscleGroup.BICEPS
            coEvery {
                exercisesRepository.createExercise(
                    id = null,
                    name = name,
                    muscleGroupList = listOf(selectedMuscleGroup),
                    videoId = null,
                    urlLink = null
                )
            }.returns(Unit)
            viewModel.onEvent(event = ExerciseManagementEvents.OnEnterName(exerciseName = name))
            viewModel.onEvent(
                event = ExerciseManagementEvents.OnMuscleGroupSelection(
                    id = MuscleGroup.values().indexOf(selectedMuscleGroup),
                    isSelected = true
                )
            )
            viewModel.onEvent(event = ExerciseManagementEvents.OnSaveExercise)

            assertEquals(name, viewModel.state.value.name)
            assertEquals(false, viewModel.state.value.nameIsBlank)
            assertEquals(false, viewModel.state.value.showMessageAboutMuscleGroup)

        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When user open an exercise, getExerciseWithMuscles from repository must be called`() =
        runTest {

            coEvery {
                exercisesRepository.getExerciseWithMuscles(exerciseId = exercise.id)
            }.returns(flowOf(exercise))
            viewModel.retrieveExercise(exerciseId = exercise.id)

            val muscleGroupsSelected =
                viewModel.state.value
                    .muscleGroupCheckBox
                    .toMutableList().apply {
                        this.forEachIndexed { index, muscleGroupCheckBox ->
                            exercise.muscleGroupList.find { muscleGroup ->
                                muscleGroup.nameRes == muscleGroupCheckBox.nameRes
                            }?.let {
                                this[index].isSelected = true
                            }
                        }
                    }

            coVerify { exercisesRepository.getExerciseWithMuscles(exerciseId = exercise.id) }
            assertEquals(exercise.id, viewModel.state.value.exerciseId)
            assertEquals(exercise.name, viewModel.state.value.name)
            assertEquals(muscleGroupsSelected, viewModel.state.value.muscleGroupCheckBox)

        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When user open an exercise, and try to delete, flag showDialog must be true`() =
        runTest {
            coEvery {
                exercisesRepository.getExerciseWithMuscles(exerciseId = exercise.id)
            }.returns(flowOf(exercise))

            viewModel.retrieveExercise(exerciseId = exercise.id)
            viewModel.onEvent(event = ExerciseManagementEvents.OnShowWarningAboutRemoving(showDialog = true))

            assertEquals(true, viewModel.state.value.showDialog)

        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When user open an exercise, and try to delete, and click on Continue button to delete, exercise must be removed`() =
        runTest {
            coEvery {
                exercisesRepository.getExerciseWithMuscles(exerciseId = exercise.id)
            }.returns(flowOf(exercise))

            coEvery {
                exercisesRepository.deleteExercise(exerciseId = exercise.id)
            }.returns(flowOf(true))

            viewModel.retrieveExercise(exerciseId = exercise.id)
            viewModel.onEvent(event = ExerciseManagementEvents.OnShowWarningAboutRemoving(showDialog = true))
            viewModel.onEvent(event = ExerciseManagementEvents.OnDeleteExercise)

            assertEquals(false, viewModel.state.value.showDialog)
            assertEquals(true, viewModel.state.value.navigateBack)
            coVerify { exercisesRepository.deleteExercise(exerciseId = exercise.id) }

        }

    private val exercise = Exercise(
        id = 1,
        name = "Biceps na barra W",
        favorite = true,
        muscleGroupList = listOf(MuscleGroup.BICEPS),
        videoId = "fakeId",
        urlLink = "fakeurl"
    )
}