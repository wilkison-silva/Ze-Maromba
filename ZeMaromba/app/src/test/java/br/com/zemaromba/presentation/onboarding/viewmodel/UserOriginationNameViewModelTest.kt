package br.com.zemaromba.presentation.onboarding.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.zemaromba.domain.repository.UserRepository
import br.com.zemaromba.presentation.onboarding.screen.event.UserOriginationNameEvents
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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


class UserOriginationNameViewModelTest {

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
    private val viewModel = UserOriginationNameViewModel(userRepository)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When event is OnSaveName, State property name should be updated with the name parameter and nameSaved should be true`() =
        runTest {
            val fakeName = "fakeName"
            coEvery {
                userRepository.saveName(name = fakeName)
            }.returns(Unit)

            viewModel.onEvent(event = UserOriginationNameEvents.OnSaveName(name = fakeName))
            advanceUntilIdle()

            assertEquals(fakeName, viewModel.state.value.name)
            assertEquals(true, viewModel.state.value.nameSaved)
            coVerify { userRepository.saveName(name = fakeName) }

        }


    @Test
    fun `When event is OnEnterNewName, State property name should be updated with the name parameter`() {
        val fakeName = "FakeName"
        viewModel.onEvent(event = UserOriginationNameEvents.OnEnterNewName(name = fakeName))

        assertEquals(fakeName, viewModel.state.value.name)
    }

}