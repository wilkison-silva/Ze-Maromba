package br.com.zemaromba.presentation.exercise.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.zemaromba.R
import br.com.zemaromba.domain.model.Exercise
import br.com.zemaromba.domain.model.ExerciseFilter
import br.com.zemaromba.domain.model.MuscleGroup
import br.com.zemaromba.domain.repository.ExercisesRepository
import br.com.zemaromba.presentation.exercises.viewmodel.ExercisesListEvents
import br.com.zemaromba.presentation.exercises.viewmodel.ExercisesListViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ExercisesListViewModelTest {
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
    private lateinit var viewModel: ExercisesListViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When viewModel is initialized, getExercisesWithMuscles() from repository must be called, State should be updated with the exercises converted to exerciesView and ExerciseFilter_ALL must be the selected one`() =
        runTest {

            coEvery {
                exercisesRepository.getExercisesWithMuscles()
            }.returns(flowOf(exercisesList))

            viewModel = ExercisesListViewModel(exercisesRepository)

            val exercisesViewList = exercisesList.map { it.toExerciseView() }
            val filterChipAll = viewModel.state.value.exerciseFilters.first {
                it.type == ExerciseFilter.ALL
            }

            assertEquals(exercisesViewList, viewModel.state.value.exercisesList)
            assertEquals(true, filterChipAll.isSelected)
            coVerify(atLeast = 2) { exercisesRepository.getExercisesWithMuscles() }

        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When event is OnFavoriteExercise, exercisesRepository_updateExerciseFavoriteField() must be called`() =
        runTest {
            val exerciseId: Long = 0
            val exerciseIcon = R.drawable.ic_star_filled
            val isFavorite = exerciseIcon != R.drawable.ic_star_filled
            coEvery {
                exercisesRepository.getExercisesWithMuscles()
            }.returns(flowOf(exercisesList))

            coEvery {
                exercisesRepository.updateExerciseFavoriteField(
                    exerciseId = exerciseId,
                    isFavorite = isFavorite
                )
            }.returns(Unit)

            viewModel = ExercisesListViewModel(exercisesRepository)
            viewModel.onEvent(
                event = ExercisesListEvents.OnFavoriteExercise(
                    exerciseId,
                    exerciseIcon
                )
            )


            coVerify(exactly = 1) {
                exercisesRepository.updateExerciseFavoriteField(
                    exerciseId,
                    isFavorite
                )
            }

        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When event is OnSearchExercise, and exerciseName to search exists in the list, filteredList must be equal to State property exercisesList and state property searchBarState_text must be equals to exercise name parameter`() =
        runTest {
            val exerciseName = "Bíceps"

            coEvery {
                exercisesRepository.getExercisesWithMuscles()
            }.returns(flowOf(exercisesList))


            viewModel = ExercisesListViewModel(exercisesRepository)
            viewModel.onEvent(
                event = ExercisesListEvents.OnSearchExercise(exerciseName = exerciseName)
            )

            advanceUntilIdle()

            val filteredList = exercisesList
                .map { it.toExerciseView() }
                .filter {
                    it.name.contains(exerciseName, ignoreCase = true)
                }

            assertEquals(exerciseName, viewModel.state.value.searchBarState.text)
            assertEquals(filteredList, viewModel.state.value.exercisesList)

        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When event is OnFilterChange, and filter is ExerciseFilter_ALL, UI chipFilter ALL must be selected, and exercises list must be the same returned from database`() =
        runTest {

            coEvery {
                exercisesRepository.getExercisesWithMuscles()
            }.returns(flowOf(exercisesList))

            viewModel = ExercisesListViewModel(exercisesRepository)
            viewModel.onEvent(event = ExercisesListEvents.OnFilterChange(ExerciseFilter.ALL))

            advanceUntilIdle()

            val filteredList = exercisesList
                .map { it.toExerciseView() }

            assertEquals(true, viewModel.state.value.exerciseFilters[0].isSelected)
            assertEquals(ExerciseFilter.ALL, viewModel.state.value.exerciseFilters[0].type)
            assertEquals(false, viewModel.state.value.exerciseFilters[1].isSelected)
            assertEquals(ExerciseFilter.MUSCLE_GROUP, viewModel.state.value.exerciseFilters[1].type)
            assertEquals(false, viewModel.state.value.exerciseFilters[2].isSelected)
            assertEquals(ExerciseFilter.FAVORITE, viewModel.state.value.exerciseFilters[2].type)
            assertEquals(filteredList, viewModel.state.value.exercisesList)

        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When event is OnFilterChange, initial filter is ALL and  change filter to ExerciseFilter_FAVORITE, chipFilter FAVORITE must be selected, and exercises list must be filtered by favorite`() =
        runTest {

            coEvery {
                exercisesRepository.getExercisesWithMuscles()
            }.returns(flowOf(exercisesList))

            viewModel = ExercisesListViewModel(exercisesRepository)
            viewModel.onEvent(event = ExercisesListEvents.OnFilterChange(ExerciseFilter.FAVORITE))

            advanceUntilIdle()

            val filteredList = exercisesList
                .filter { exercise -> exercise.isFavorite }
                .map { it.toExerciseView() }


            assertEquals(false, viewModel.state.value.exerciseFilters[0].isSelected)
            assertEquals(ExerciseFilter.ALL, viewModel.state.value.exerciseFilters[0].type)
            assertEquals(false, viewModel.state.value.exerciseFilters[1].isSelected)
            assertEquals(ExerciseFilter.MUSCLE_GROUP, viewModel.state.value.exerciseFilters[1].type)
            assertEquals(true, viewModel.state.value.exerciseFilters[2].isSelected)
            assertEquals(ExerciseFilter.FAVORITE, viewModel.state.value.exerciseFilters[2].type)
            assertEquals(filteredList, viewModel.state.value.exercisesList)

        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When event is OnFilterChange, initial filter is ALL and user double tap on ExerciseFilter_FAVORITE twice, UI chipFilter ALL must be selected, and exercises list must be the same returned from database`() =
        runTest {

            coEvery {
                exercisesRepository.getExercisesWithMuscles()
            }.returns(flowOf(exercisesList))

            viewModel = ExercisesListViewModel(exercisesRepository)
            viewModel.onEvent(event = ExercisesListEvents.OnFilterChange(ExerciseFilter.FAVORITE))
            viewModel.onEvent(event = ExercisesListEvents.OnFilterChange(ExerciseFilter.FAVORITE))

            advanceUntilIdle()

            val filteredList = exercisesList
                .map { it.toExerciseView() }

            assertEquals(true, viewModel.state.value.exerciseFilters[0].isSelected)
            assertEquals(ExerciseFilter.ALL, viewModel.state.value.exerciseFilters[0].type)
            assertEquals(false, viewModel.state.value.exerciseFilters[1].isSelected)
            assertEquals(ExerciseFilter.MUSCLE_GROUP, viewModel.state.value.exerciseFilters[1].type)
            assertEquals(false, viewModel.state.value.exerciseFilters[2].isSelected)
            assertEquals(ExerciseFilter.FAVORITE, viewModel.state.value.exerciseFilters[2].type)
            assertEquals(filteredList, viewModel.state.value.exercisesList)

        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When event is OnFilterChange, initial filter is ALL and user choose filter by Muscle Group chip Muscle group must be selected and exercises list must be filtered by selected muscle groups`() =
        runTest {

            val selectedMuscleGroup = MuscleGroup.BICEPS

            coEvery {
                exercisesRepository.getExercisesWithMuscles()
            }.returns(flowOf(exercisesList))

            viewModel = ExercisesListViewModel(exercisesRepository)
            viewModel.onEvent(event = ExercisesListEvents.OnFilterChange(ExerciseFilter.MUSCLE_GROUP))

            viewModel.onEvent(
                event = ExercisesListEvents.OnMuscleGroupSelection(
                    id = MuscleGroup.values().indexOf(selectedMuscleGroup),
                    isSelected = true
                )
            )

            viewModel.onEvent(event = ExercisesListEvents.OnFilterBySelectedMuscleGroups)

            advanceUntilIdle()

            val filteredList = exercisesList
                .filter { it.muscleGroupList.contains(selectedMuscleGroup) }
                .map { it.toExerciseView() }

            assertEquals(false, viewModel.state.value.exerciseFilters[0].isSelected)
            assertEquals(ExerciseFilter.ALL, viewModel.state.value.exerciseFilters[0].type)
            assertEquals(true, viewModel.state.value.exerciseFilters[1].isSelected)
            assertEquals(ExerciseFilter.MUSCLE_GROUP, viewModel.state.value.exerciseFilters[1].type)
            assertEquals(false, viewModel.state.value.exerciseFilters[2].isSelected)
            assertEquals(ExerciseFilter.FAVORITE, viewModel.state.value.exerciseFilters[2].type)
            assertEquals(filteredList, viewModel.state.value.exercisesList)

        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When event is OnFilterChange, initial filter is ALL and user choose filter by Muscle Group and then filter by favorite, chip Muscle Group and Favorite must be selected and exercises list must be filtered by both`() =
        runTest {

            val selectedMuscleGroup = MuscleGroup.BICEPS

            coEvery {
                exercisesRepository.getExercisesWithMuscles()
            }.returns(flowOf(exercisesList))

            viewModel = ExercisesListViewModel(exercisesRepository)
            viewModel.onEvent(event = ExercisesListEvents.OnFilterChange(ExerciseFilter.MUSCLE_GROUP))

            viewModel.onEvent(
                event = ExercisesListEvents.OnMuscleGroupSelection(
                    id = MuscleGroup.values().indexOf(selectedMuscleGroup),
                    isSelected = true
                )
            )

            viewModel.onEvent(event = ExercisesListEvents.OnFilterBySelectedMuscleGroups)

            viewModel.onEvent(event = ExercisesListEvents.OnFilterChange(exerciseFilter = ExerciseFilter.FAVORITE))

            advanceUntilIdle()

            val filteredList = exercisesList
                .filter { it.muscleGroupList.contains(selectedMuscleGroup) }
                .filter { it.isFavorite }
                .map { it.toExerciseView() }

            assertEquals(false, viewModel.state.value.exerciseFilters[0].isSelected)
            assertEquals(ExerciseFilter.ALL, viewModel.state.value.exerciseFilters[0].type)
            assertEquals(true, viewModel.state.value.exerciseFilters[1].isSelected)
            assertEquals(ExerciseFilter.MUSCLE_GROUP, viewModel.state.value.exerciseFilters[1].type)
            assertEquals(true, viewModel.state.value.exerciseFilters[2].isSelected)
            assertEquals(ExerciseFilter.FAVORITE, viewModel.state.value.exerciseFilters[2].type)
            assertEquals(filteredList, viewModel.state.value.exercisesList)

        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When event is OnFilterChange, initial filter is ALL and user choose filter by favorite and then by Muscle Group, chip Muscle Group and Favorite must be selected and exercises list must be filtered by both`() =
        runTest {

            val selectedMuscleGroup = MuscleGroup.BICEPS

            coEvery {
                exercisesRepository.getExercisesWithMuscles()
            }.returns(flowOf(exercisesList))

            viewModel = ExercisesListViewModel(exercisesRepository)

            viewModel.onEvent(event = ExercisesListEvents.OnFilterChange(exerciseFilter = ExerciseFilter.FAVORITE))

            viewModel.onEvent(event = ExercisesListEvents.OnFilterChange(ExerciseFilter.MUSCLE_GROUP))

            viewModel.onEvent(
                event = ExercisesListEvents.OnMuscleGroupSelection(
                    id = MuscleGroup.values().indexOf(selectedMuscleGroup),
                    isSelected = true
                )
            )

            viewModel.onEvent(event = ExercisesListEvents.OnFilterBySelectedMuscleGroups)

            advanceUntilIdle()

            val filteredList = exercisesList
                .filter { it.muscleGroupList.contains(selectedMuscleGroup) }
                .filter { it.isFavorite }
                .map { it.toExerciseView() }

            assertEquals(false, viewModel.state.value.exerciseFilters[0].isSelected)
            assertEquals(ExerciseFilter.ALL, viewModel.state.value.exerciseFilters[0].type)
            assertEquals(true, viewModel.state.value.exerciseFilters[1].isSelected)
            assertEquals(ExerciseFilter.MUSCLE_GROUP, viewModel.state.value.exerciseFilters[1].type)
            assertEquals(true, viewModel.state.value.exerciseFilters[2].isSelected)
            assertEquals(ExerciseFilter.FAVORITE, viewModel.state.value.exerciseFilters[2].type)
            assertEquals(filteredList, viewModel.state.value.exercisesList)

        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When event is OnFilterChange, initial filter is ALL and user choose filter by Muscle Group and then by ALL, chip ALL must be selected and exercises list must be the original from database`() =
        runTest {

            val selectedMuscleGroup = MuscleGroup.BICEPS

            coEvery {
                exercisesRepository.getExercisesWithMuscles()
            }.returns(flowOf(exercisesList))

            viewModel = ExercisesListViewModel(exercisesRepository)

            viewModel.onEvent(event = ExercisesListEvents.OnFilterChange(ExerciseFilter.MUSCLE_GROUP))

            viewModel.onEvent(
                event = ExercisesListEvents.OnMuscleGroupSelection(
                    id = MuscleGroup.values().indexOf(selectedMuscleGroup),
                    isSelected = true
                )
            )

            viewModel.onEvent(event = ExercisesListEvents.OnFilterBySelectedMuscleGroups)

            viewModel.onEvent(event = ExercisesListEvents.OnFilterChange(ExerciseFilter.ALL))

            advanceUntilIdle()

            val filteredList = exercisesList
                .map { it.toExerciseView() }

            assertEquals(true, viewModel.state.value.exerciseFilters[0].isSelected)
            assertEquals(ExerciseFilter.ALL, viewModel.state.value.exerciseFilters[0].type)
            assertEquals(false, viewModel.state.value.exerciseFilters[1].isSelected)
            assertEquals(ExerciseFilter.MUSCLE_GROUP, viewModel.state.value.exerciseFilters[1].type)
            assertEquals(false, viewModel.state.value.exerciseFilters[2].isSelected)
            assertEquals(ExerciseFilter.FAVORITE, viewModel.state.value.exerciseFilters[2].type)
            assertEquals(filteredList, viewModel.state.value.exercisesList)

        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When event is OnFilterChange, initial filter is ALL and user choose filter by  Muscle Group and does not select any muscle group, chip ALL must be selected and exercises list must be the original`() =
        runTest {

            coEvery {
                exercisesRepository.getExercisesWithMuscles()
            }.returns(flowOf(exercisesList))


            viewModel = ExercisesListViewModel(exercisesRepository)

            viewModel.onEvent(event = ExercisesListEvents.OnFilterChange(ExerciseFilter.MUSCLE_GROUP))

            viewModel.onEvent(event = ExercisesListEvents.OnFilterBySelectedMuscleGroups)

            advanceUntilIdle()

            val filteredList = exercisesList
                .map { it.toExerciseView() }

            assertEquals(true, viewModel.state.value.exerciseFilters[0].isSelected)
            assertEquals(ExerciseFilter.ALL, viewModel.state.value.exerciseFilters[0].type)
            assertEquals(false, viewModel.state.value.exerciseFilters[1].isSelected)
            assertEquals(ExerciseFilter.MUSCLE_GROUP, viewModel.state.value.exerciseFilters[1].type)
            assertEquals(false, viewModel.state.value.exerciseFilters[2].isSelected)
            assertEquals(ExerciseFilter.FAVORITE, viewModel.state.value.exerciseFilters[2].type)
            assertEquals(filteredList, viewModel.state.value.exercisesList)
        }


    private val exercisesList = listOf(
        Exercise(
            id = 1,
            name = "Bíceps na polia",
            isFavorite = false,
            muscleGroupList = listOf(MuscleGroup.BICEPS),
            urlLink = null,
            videoId = null,
            mayExclude = true
        ),
        Exercise(
            id = 1,
            name = "Rosca na barra W",
            isFavorite = true,
            muscleGroupList = listOf(MuscleGroup.BICEPS, MuscleGroup.FOREARM),
            urlLink = null,
            videoId = null,
            mayExclude = true
        ),
        Exercise(
            id = 2,
            name = "Agachamento livre",
            isFavorite = true,
            muscleGroupList = listOf(MuscleGroup.ABDUCTORS, MuscleGroup.LUMBAR, MuscleGroup.GLUTES),
            urlLink = null,
            videoId = null,
            mayExclude = true
        )
    )
}