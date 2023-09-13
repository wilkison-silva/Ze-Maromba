package br.com.zemaromba.presentation.onboarding.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.zemaromba.domain.repository.UserRepository
import io.mockk.coEvery
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

class GetStartedViewModelTest {

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

    private val userRepository = mockk<UserRepository>()
    private lateinit var viewModel: GetStartedViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When viewModel is initialized, and name saved on DataStore is not blank, State should be updated with userNameIsValid with true and userName with the string saved in DataStore`() =
        runTest {

            val fakeName = "fakeName"

            coEvery {
                userRepository.getName()
            }.returns(flowOf(fakeName))

            viewModel = GetStartedViewModel(userRepository)

            advanceUntilIdle()

            assertEquals(fakeName, viewModel.state.value.userName)
            assertEquals(true, viewModel.state.value.userNameIsValid)


        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When viewModel is initialized, and name saved on DataStore is blank, State should be updated with userNameIsValid with false and userName must be equals empty string`() =
        runTest {

            val emptyName = ""

            coEvery {
                userRepository.getName()
            }.returns(flowOf(emptyName))

            viewModel = GetStartedViewModel(userRepository)

            advanceUntilIdle()

            assertEquals(emptyName, viewModel.state.value.userName)
            assertEquals(false, viewModel.state.value.userNameIsValid)

        }
}