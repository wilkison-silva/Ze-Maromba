package br.com.zemaromba.feature.onboarding.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.zemaromba.core_domain.datastore.UserDataStore
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
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

    private val userDataStore = mockk<UserDataStore>()
    private val viewModel = UserOriginationNameViewModel(userDataStore)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When event is OnSaveName, State property name should be updated with the name parameter and nameSaved should be true`() =
        runTest {
            val fakeName = "fakeName"
            coEvery {
                userDataStore.saveName(name = fakeName)
            }.returns(Unit)

            viewModel.onEvent(event = UserOriginationNameEvents.OnSaveName(name = fakeName))
            advanceUntilIdle()

            assertEquals(fakeName, viewModel.state.value.name)
            assertEquals(true, viewModel.state.value.nameSaved)
            coVerify { userDataStore.saveName(name = fakeName) }

        }


    @Test
    fun `When event is OnEnterNewName, State property name should be updated with the name parameter`() {
        val fakeName = "FakeName"
        viewModel.onEvent(event = UserOriginationNameEvents.OnEnterNewName(name = fakeName))

        assertEquals(fakeName, viewModel.state.value.name)
    }

}