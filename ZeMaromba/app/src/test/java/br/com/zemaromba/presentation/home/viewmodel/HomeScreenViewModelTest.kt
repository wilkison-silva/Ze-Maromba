package br.com.zemaromba.presentation.home.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.zemaromba.domain.repository.UserRepository
import br.com.zemaromba.presentation.home.viewmodel.HomeScreenViewModel
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

    private val userRepository = mockk<UserRepository>()
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
    fun `When viewmodel is initialized and userName was saved, userName must be retrieved from databases`() =
        runTest {
            val fakeName = "fakeName"
            coEvery {
                userRepository.getName()
            }.returns(flowOf(fakeName))


            viewModel = HomeScreenViewModel(userRepository)

            coVerify { userRepository.getName() }
            assertEquals(fakeName, viewModel.state.value.userName)
        }
}