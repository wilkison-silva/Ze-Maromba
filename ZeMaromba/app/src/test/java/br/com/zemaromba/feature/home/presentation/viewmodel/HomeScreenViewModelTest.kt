package br.com.zemaromba.feature.home.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.zemaromba.common.extensions.toTrainingPlanView
import br.com.zemaromba.core_domain.datastore.UserDataStore
import br.com.zemaromba.core_domain.model.TrainingPlan
import br.com.zemaromba.feature.home.domain.repository.TrainingPlanRepository
import br.com.zemaromba.feature.onboarding.presentation.viewmodel.GetStartedViewModel
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

class HomeScreenViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val userDataStore = mockk<UserDataStore>()
    private val trainingPlanRepository = mockk<TrainingPlanRepository>()
    private lateinit var viewModel: HomeScreenViewModel

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
    fun `When viewmodel is initialized, userName was saved and there is some training plan saved, userName and training plan list must be retrieved from databases`() =
        runTest {
            val fakeName = "fakeName"
            val fakeTrainingPlans = listOf(
                TrainingPlan(
                    id = 1,
                    name = "Teste1",
                    trainings = emptyList()
                )
            )
            coEvery {
                userDataStore.getName()
            }.returns(flowOf(fakeName))
            coEvery {
                trainingPlanRepository.getAllTrainingPlans()
            }.returns(flowOf(fakeTrainingPlans))

            viewModel = HomeScreenViewModel(userDataStore, trainingPlanRepository)

            val fakeTrainingPlansView = fakeTrainingPlans.map { it.toTrainingPlanView() }

            coVerify { userDataStore.getName() }
            coVerify { trainingPlanRepository.getAllTrainingPlans() }
            assertEquals(fakeName, viewModel.state.value.userName)
            assertEquals(fakeTrainingPlansView, viewModel.state.value.trainingPlanList)
            assertEquals(false, viewModel.state.value.showMessage)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When viewmodel is initialized, userName was saved and there is no training plan saved, userName must be retrieved and showMessage about create first training must be true`() =
        runTest {
            val fakeName = "fakeName"
            val fakeTrainingPlans = listOf<TrainingPlan>()
            coEvery {
                userDataStore.getName()
            }.returns(flowOf(fakeName))
            coEvery {
                trainingPlanRepository.getAllTrainingPlans()
            }.returns(flowOf(fakeTrainingPlans))

            viewModel = HomeScreenViewModel(userDataStore, trainingPlanRepository)

            val fakeTrainingPlansView = fakeTrainingPlans.map { it.toTrainingPlanView() }

            coVerify { userDataStore.getName() }
            coVerify { trainingPlanRepository.getAllTrainingPlans() }
            assertEquals(fakeName, viewModel.state.value.userName)
            assertEquals(fakeTrainingPlansView, viewModel.state.value.trainingPlanList)
            assertEquals(true, viewModel.state.value.showMessage)
        }


}