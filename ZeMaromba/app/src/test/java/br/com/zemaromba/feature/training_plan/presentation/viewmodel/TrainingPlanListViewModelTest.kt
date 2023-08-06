package br.com.zemaromba.feature.training_plan.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.zemaromba.common.extensions.toTrainingPlanView
import br.com.zemaromba.core_domain.model.TrainingPlan
import br.com.zemaromba.feature.training_plan.domain.repository.TrainingPlanRepository
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

class TrainingPlanListViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val trainingPlanRepository = mockk<TrainingPlanRepository>()
    private lateinit var viewModel: TrainingPlanListViewModel

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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When viewmodel is initialized, there is some training plan saved, fakeTraininPlans should be set to state property and showMessage should be false`() =
        runTest {
            val fakeTrainingPlans = listOf(
                TrainingPlan(
                    id = 1,
                    name = "Teste1",
                    trainings = emptyList()
                )
            )
            coEvery {
                trainingPlanRepository.getAllTrainingPlans()
            }.returns(flowOf(fakeTrainingPlans))

            viewModel = TrainingPlanListViewModel(trainingPlanRepository)

            val fakeTrainingPlansView = fakeTrainingPlans.map { it.toTrainingPlanView() }

            coVerify { trainingPlanRepository.getAllTrainingPlans() }
            assertEquals(fakeTrainingPlansView, viewModel.state.value.trainingPlanList)
            assertEquals(false, viewModel.state.value.showMessage)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When viewmodel is initialized, userName was saved and there is no training plan saved, userName must be retrieved and showMessage about create first training must be true`() =
        runTest {
            val fakeTrainingPlans = listOf<TrainingPlan>()
            coEvery {
                trainingPlanRepository.getAllTrainingPlans()
            }.returns(flowOf(fakeTrainingPlans))

            viewModel = TrainingPlanListViewModel(trainingPlanRepository)

            val fakeTrainingPlansView = fakeTrainingPlans.map { it.toTrainingPlanView() }

            coVerify { trainingPlanRepository.getAllTrainingPlans() }
            assertEquals(fakeTrainingPlansView, viewModel.state.value.trainingPlanList)
            assertEquals(true, viewModel.state.value.showMessage)
        }
}